package gr.aueb.cf.carrentalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing the response to a successful authentication request.
 * This class contains user information and the generated JWT token.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {

    /**
     * The first name of the authenticated user.
     */
    private String firstname;

    /**
     * The last name of the authenticated user.
     */
    private String lastname;

    /**
     * The role assigned to the authenticated user (SIMPLE_USER, SUPER_ADMIN).
     */
    private String role;

    /**
     * The JWT token generated upon successful authentication.
     * This token is used to authorize subsequent API requests.
     */
    private String token;
}
