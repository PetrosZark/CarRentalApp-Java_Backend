package gr.aueb.cf.carrentalapp.service;

import gr.aueb.cf.carrentalapp.core.enums.Role;
import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.carrentalapp.core.filters.Paginated;
import gr.aueb.cf.carrentalapp.core.filters.UserFilters;
import gr.aueb.cf.carrentalapp.core.specifications.UserSpecification;
import gr.aueb.cf.carrentalapp.dto.UserInsertDTO;
import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.aueb.cf.carrentalapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.carrentalapp.mapper.Mapper;
import gr.aueb.cf.carrentalapp.model.User;
import gr.aueb.cf.carrentalapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling user-related operations.
 * Provides methods to manage user creation, updates, deletion, and filtering.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final Mapper mapper;

    /**
     * Creates a new user based on the provided DTO.
     * Ensures that no duplicate user data exists before saving.
     *
     * @param dto The data transfer object containing user details.
     * @return UserReadOnlyDTO representing the saved user.
     * @throws AppObjectAlreadyExistsException if a user with the same email, VAT, phone, or username already exists.
     */
    @Transactional(rollbackOn = Exception.class)
    public UserReadOnlyDTO saveUser(UserInsertDTO dto) throws AppObjectAlreadyExistsException {
        LOGGER.info("Checking for duplicate email: {}", dto.getEmail());

        // Check for duplicate email
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            LOGGER.error("User with email {} already exists", dto.getEmail());
            throw new AppObjectAlreadyExistsException("User", "User with email " + dto.getEmail()
                    + " already exists");
        }

        // Check for duplicate VAT
        if (userRepository.findByVat(dto.getVat()).isPresent()) {
            LOGGER.error("User with VAT {} already exists", dto.getVat());
            throw new AppObjectAlreadyExistsException("User", "User with vat " + dto.getVat()
                    + " already exists");
        }

        // Check for duplicate username
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new AppObjectAlreadyExistsException("User", "User with username " + dto.getUsername()
                    + " already exists");
        }

        // Check for duplicate phone number
        if (userRepository.findByPhone(dto.getPhone()).isPresent()) {
            throw new AppObjectAlreadyExistsException("User", "User with phone number " + dto.getPhone()
                    + " already exists");
        }


        // Map DTO to entity and save
        LOGGER.info("Mapping DTO to entity: {}", dto);
        User user = mapper.mapToUserEntity(dto);

        try {
            // Persist the new user entity to the database
            User savedUser = userRepository.save(user);

            // Return the saved user as a read-only DTO
            return mapper.mapToUserReadOnlyDTO(savedUser);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Data integrity violation while saving user: {}", user, e);
            throw new AppObjectAlreadyExistsException("User", "Duplicate user data found");
        }
    }

    /**
     * Toggles the active status of a user by their username.
     *
     * @param username The username of the user whose status will be toggled.
     * @return UserReadOnlyDTO representing the updated user.
     * @throws AppObjectNotFoundException if the user is not found.
     */
    public UserReadOnlyDTO toggleUserStatus(String username) throws AppObjectNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppObjectNotFoundException("User", "User with username: " + username + " not found"));

        // Toggle the active status
        user.setIsActive(!user.getIsActive());
        User updatedUser = userRepository.save(user);

        // Return the updated user as a read-only DTO
        return mapper.mapToUserReadOnlyDTO(updatedUser);
    }

    /**
     * Updates the role of a user by their username.
     *
     * @param username The username of the user whose role will be updated.
     * @param newRole  The new role to assign to the user.
     * @return UserReadOnlyDTO representing the updated user.
     * @throws AppObjectNotFoundException if the user is not found.
     */
    @Transactional
    public UserReadOnlyDTO changeUserRole(String username, Role newRole) throws AppObjectNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppObjectNotFoundException("User", "User with username: " + username + " not found"));

        // Update user role
        user.setRole(newRole);
        User updatedUser = userRepository.save(user);

        // Return the updated user as a read-only DTO
        return mapper.mapToUserReadOnlyDTO(updatedUser);
    }

    /**
     * Deletes a user by their username.
     *
     * @param username The username of the user to delete.
     * @throws AppObjectNotFoundException if the user is not found.
     */
    @Transactional
    public void deleteUser(String username) throws AppObjectNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppObjectNotFoundException("User", "User with username: " + username + " not found"));

        // Perform user deletion
        userRepository.delete(user);
        LOGGER.info("User with username {} successfully deleted", username);
    }

    /**
     * Retrieves a filtered and paginated list of users based on provided filters.
     *
     * @param filters The filters to apply for user retrieval.
     * @return Paginated list of UserReadOnlyDTOs matching the filters.
     */
    @Transactional
    public Paginated<UserReadOnlyDTO> getUsersFilteredPaginated(UserFilters filters) {
        var filtered = userRepository.findAll(getSpecsFromFilters(filters), filters.getPageable());
        return new Paginated<>(filtered.map(mapper::mapToUserReadOnlyDTO));
    }

    /**
     * Builds user filtering specifications based on provided filter criteria.
     *
     * @param filters The filters to apply.
     * @return Specification for filtering users.
     */
    private Specification<User> getSpecsFromFilters(UserFilters filters) {
        return Specification
                .where(UserSpecification.userVatIs(filters.getVat()))
                .and(UserSpecification.userIsActive(filters.getIsActive()));
    }


}
