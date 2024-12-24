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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInsertDTO {

    @NotEmpty(message = "Username cannot be empty.")
    @Size(min = 6, message = "Username must be at least 6 characters long.")
    private String username;

    @NotEmpty(message = "Password cannot be empty.")
    @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[@#$%!^&*]).{8,}$", message = "Invalid Password")
    private String password;

    @NotEmpty(message = "Firstname cannot be empty.")
    @Size(min = 2, message = "Firstname must be at least 2 characters long.")
    private String firstname;

    @NotEmpty(message = "Lastname cannot be empty.")
    @Size(min = 2, message = "Lastname must be at least 2 characters long.")
    private String lastname;

    @NotEmpty(message = "Email cannot be empty.")
    private String email;

    @NotEmpty(message = "Vat cannot be empty.")
    @Pattern(regexp = "^\\d{9}$", message = "Vat must be 9 digits long.")
    private String vat;

    @NotEmpty(message = "Phone number cannot be empty. ")
    @Pattern(regexp = "^\\d{10,18}$", message = "Phone number must be between 9 and 18 digits long.")
    private String phone;

    @NotNull(message = "Date of birth cannot be empty.")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender must not be null")
    private Gender gender;

    private Role role;

    private Boolean isActive;
}
