package gr.aueb.cf.carrentalapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;



/**
 * Service class for handling JWT (JSON Web Token) operations.
 * This class provides methods to generate, validate, and extract data from JWT tokens.
 */
@Service
public class JwtService {

    // Secret key used to sign the JWT. It should be stored securely and kept private.
    private final String secretKey = "5a957dbdf70202e6a0d048ec56abc0274a5b3f68f542240ac04dd519799aba78";

    // Token expiration time set to 12 hours (in milliseconds).
    private final long jwtExpiration = 43_200_000;

    /**
     * Generates a JWT token for the specified username and role.
     *
     * @param username The username for which the token is generated.
     * @param role     The user's role to include in the token claims.
     * @return A signed JWT token.
     */
    public String generateToken(String username, String role) {
        var claims = new HashMap<String, Object>();
        claims.put("role", role);
        return Jwts
                .builder()
                .setIssuer("self")
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the provided JWT token.
     *
     * @param token        The JWT token to validate.
     * @param userDetails  UserDetails object of the currently authenticated user.
     * @return true if the token is valid and matches the user, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String subject = extractSubject(token);
        return (subject.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token The JWT token.
     * @param claim The claim to extract.
     * @return The value of the claim as a string.
     */
    public String getStringClaim(String token, String claim) {
        return extractAllClaims(token).get(claim, String.class);
    }

    /**
     * Extracts the subject from the JWT token.
     *
     * @param token The JWT token.
     * @return The subject from the token.
     */
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a claim from the JWT using a custom function.
     *
     * @param token          The JWT token.
     * @param claimsResolver A function to extract the claim.
     * @param <T>            The type of the claim to return.
     * @return The claim extracted from the token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Checks if the token is expired.
     *
     * @param token The JWT token to check.
     * @return true if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date of the token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token.
     * @return All claims contained in the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the signing key used to verify and sign JWT tokens.
     *
     * @return The signing key derived from the secret key.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
