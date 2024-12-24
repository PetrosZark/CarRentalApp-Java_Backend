package gr.aueb.cf.carrentalapp.dto;

import gr.aueb.cf.carrentalapp.core.enums.Gender;
import gr.aueb.cf.carrentalapp.core.enums.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for inserting a new user into the system.
 * This class ensures validation of user input fields during registration.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInsertDTO {

    /**
     * The username of the user.
     * Must be at least 6 characters long and cannot be empty.
     */
    @NotEmpty(message = "Username cannot be empty.")
    @Size(min = 6, message = "Username must be at least 6 characters long.")
    private String username;

    /**
     * The password of the user.
     * Must contain at least one lowercase letter, one uppercase letter, one digit,
     * and one special character, and must be at least 8 characters long.
     */
    @NotEmpty(message = "Password cannot be empty.")
    @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[@#$%!^&*]).{8,}$", message = "Invalid Password")
    private String password;

    /**
     * The first name of the user.
     * Must be at least 2 characters long and cannot be empty.
     */
    @NotEmpty(message = "Firstname cannot be empty.")
    @Size(min = 2, message = "Firstname must be at least 2 characters long.")
    private String firstname;

    /**
     * The last name of the user.
     * Must be at least 2 characters long and cannot be empty.
     */
    @NotEmpty(message = "Lastname cannot be empty.")
    @Size(min = 2, message = "Lastname must be at least 2 characters long.")
    private String lastname;

    /**
     * The email of the user.
     * This field is required and cannot be empty.
     */
    @NotEmpty(message = "Email cannot be empty.")
    private String email;

    /**
     * The VAT (Tax Identification Number) of the user.
     * Must be exactly 9 digits long.
     */
    @NotEmpty(message = "Vat cannot be empty.")
    @Pattern(regexp = "^\\d{9}$", message = "Vat must be 9 digits long.")
    private String vat;

    /**
     * The phone number of the user.
     * Must be between 10 and 18 digits long.
     */
    @NotEmpty(message = "Phone number cannot be empty. ")
    @Pattern(regexp = "^\\d{10,18}$", message = "Phone number must be between 9 and 18 digits long.")
    private String phone;

    /**
     * The date of birth of the user.
     * This field is required and cannot be null.
     */
    @NotNull(message = "Date of birth cannot be empty.")
    private LocalDate dateOfBirth;

    /**
     * The gender of the user.
     * This field must not be null.
     */
    @NotNull(message = "Gender must not be null")
    private Gender gender;

    /**
     * The role of the user (SIMPLE_USER, SUPER_ADMIN).
     * This field by default is SIMPLE_USER and can only be changed by a SUPER_ADMIN.
     */
    private Role role;

    /**
     * The active status of the user.
     * If true, the user is active; otherwise, the user is inactive.
     * This field by default is true and can only be changed by a SUPER_ADMIN.
     */
    private Boolean isActive;
}
