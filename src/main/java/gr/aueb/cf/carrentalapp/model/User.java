package gr.aueb.cf.carrentalapp.model;

import gr.aueb.cf.carrentalapp.core.enums.Gender;
import gr.aueb.cf.carrentalapp.core.enums.Role;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.util.*;


/**
 * Represents a user in the car rental system.
 * This entity stores user details, roles, and authentication information.
 * Implements UserDetails for integration with Spring Security.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users") // Maps the entity to the 'users' table in the database
public class User extends AbstractEntity implements UserDetails {

    /**
     * Unique identifier for the user (Primary Key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique username for the user, required for authentication.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Hashed password used for user authentication.
     */
    private String password;

    /**
     * User's first name.
     */
    @Column(nullable = false)
    private String firstname;

    /**
     * User's last name.
     */
    @Column(nullable = false)
    private String lastname;

    /**
     * User's VAT (tax identification number).
     * Must be unique and cannot be null.
     */
    @Column(nullable = false, unique = true)
    private String vat;

    /**
     * User's email address.
     * Must be unique and cannot be null.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * User's phone number.
     * Must be unique and cannot be null.
     */
    @Column(nullable = false, unique = true)
    private String phone;

    /**
     * User's date of birth.
     */
    private LocalDate dateOfBirth;

    /**
     * Indicates if the user is active.
     * Defaults to true.
     */
    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

    /**
     * User's gender (Male, Female, Other).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    /**
     * Role of the user (SIMPLE_USER or SUPER_ADMIN).
     * Defaults to SIMPLE_USER.
     */
    @ColumnDefault("'SIMPLE_USER'")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * One-to-Many relationship with the Car entity.
     * A user can own multiple cars.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Car> cars = new HashSet<>();


    /**
     * Initializes default values before persisting the entity.
     * This method ensures the user is active by default and assigned the role SIMPLE_USER.
     */
    @PrePersist
    public void initializeDefaults() {
        if (isActive == null) isActive = true;
        if (role == null) role = Role.SIMPLE_USER;
    }

    /**
     * Retrieves the username for authentication.
     *
     * @return the username of the user
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the hashed password for authentication.
     *
     * @return the user's hashed password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Checks if the account is expired.
     * Always returns true (accounts never expire in this implementation).
     *
     * @return true if the account is non-expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Checks if the account is locked.
     * Always returns true (accounts are never locked in this implementation).
     *
     * @return true if the account is non-locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Checks if the credentials (password) are expired.
     * Always returns true (credentials never expire in this implementation).
     *
     * @return true if the credentials are non-expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Checks if the user is enabled (active).
     * A user is considered enabled if the isActive field is true or null.
     *
     * @return true if the user is active
     */
    @Override
    public boolean isEnabled() {
        return this.getIsActive() == null || this.getIsActive();
    }

    /**
     * Retrieves the authorities (roles) granted to the user.
     * This method returns a list containing the user's role.
     *
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
