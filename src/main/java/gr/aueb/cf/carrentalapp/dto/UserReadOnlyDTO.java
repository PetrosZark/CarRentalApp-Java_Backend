package gr.aueb.cf.carrentalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a read-only view of a user.
 * This class is used to return user details to the client without exposing sensitive or modifiable data.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserReadOnlyDTO {

    /**
     * The unique identifier of the user.
     */
    private Long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The first name of the user.
     */
    private String firstname;

    /**
     * The last name of the user.
     */
    private String lastname;

    /**
     * The VAT (Tax Identification Number) of the user.
     */
    private String vat;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The phone number of the user.
     */
    private String phone;

    /**
     * The date of birth of the user.
     */
    private LocalDate dateOfBirth;

    /**
     * The timestamp indicating when the user record was created.
     */
    private LocalDateTime createdAt;

    /**
     * The timestamp indicating the last update to the user record.
     */
    private LocalDateTime updatedAt;

    /**
     * The active status of the user.
     * True if the user is active, false otherwise.
     */
    private Boolean isActive;

    /**
     * The role of the user (SIMPLE_USER, SUPER_ADMIN).
     */
    private String role;
}
