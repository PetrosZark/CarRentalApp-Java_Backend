package gr.aueb.cf.carrentalapp.repository;

import gr.aueb.cf.carrentalapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

/**
 * Repository interface for managing User entities.
 * <p>
 * Extends JpaRepository to provide standard CRUD operations.
 * Implements JpaSpecificationExecutor to support dynamic queries through specifications.
 * </p>
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Finds a User by their email.
     * <p>
     * This method searches for a User entity based on the provided email.
     * Email is expected to be unique for each user.
     * </p>
     *
     * @param email the email address to search for
     * @return an Optional containing the User entity if found, otherwise empty
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a User by their VAT (Value Added Tax) number.
     * <p>
     * VAT is a unique identifier for users, typically used for tax purposes.
     * </p>
     *
     * @param vat the VAT number to search for
     * @return an Optional containing the User entity if found, otherwise empty
     */
    Optional<User> findByVat(String vat);

    /**
     * Finds a User by their username.
     * <p>
     * Usernames are unique and are often used for authentication purposes.
     * </p>
     *
     * @param username the username to search for
     * @return an Optional containing the User entity if found, otherwise empty
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a User by their phone number.
     * <p>
     * Phone numbers must be unique across users and can be used by a user to contact a car owner user.
     * </p>
     *
     * @param phone the phone number to search for
     * @return an Optional containing the User entity if found, otherwise empty
     */
    Optional<User> findByPhone(String phone);
}
