package gr.aueb.cf.carrentalapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Data Transfer Object (DTO) representing an authentication request.
 * This class is used to capture and validate user credentials
 * when attempting to log in.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {

    /**
     * The username provided by the user for authentication.
     * This field is required and cannot be null.
     */
    @NotNull
    private String username;

    /**
     * The password provided by the user for authentication.
     * This field is required and cannot be null.
     */
    @NotNull
    private String password;
}
