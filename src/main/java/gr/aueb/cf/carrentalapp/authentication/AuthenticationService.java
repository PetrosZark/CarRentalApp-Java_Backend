package gr.aueb.cf.carrentalapp.authentication;

import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectNotAuthorizedException;
import gr.aueb.cf.carrentalapp.dto.AuthenticationRequestDTO;
import gr.aueb.cf.carrentalapp.dto.AuthenticationResponseDTO;
import gr.aueb.cf.carrentalapp.model.User;
import gr.aueb.cf.carrentalapp.repository.UserRepository;
import gr.aueb.cf.carrentalapp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling user authentication and JWT generation.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     * Authenticates a user and generates a JWT token upon successful login.
     *
     * @param dto Authentication request containing username and password.
     * @return AuthenticationResponseDTO with user details and generated JWT token.
     * @throws AppObjectNotAuthorizedException if authentication fails or user is not found.
     */
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO dto)
            throws AppObjectNotAuthorizedException {

        // Authenticate the user using Spring Security's AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        // Retrieve user details from the database
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new AppObjectNotAuthorizedException("User", "User not authorized."));

        // Generate JWT token with user's role
        String token = jwtService.generateToken(authentication.getName(), user.getRole().name());

        // Return authentication response with user info and token
        return new AuthenticationResponseDTO(user.getFirstname(), user.getLastname(),user.getRole().name(), token);
    }
}
