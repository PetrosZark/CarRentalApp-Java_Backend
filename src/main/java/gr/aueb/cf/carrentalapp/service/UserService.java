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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final Mapper mapper;

    @Transactional(rollbackOn = Exception.class)
    public UserReadOnlyDTO saveUser(UserInsertDTO dto) throws AppObjectAlreadyExistsException {
        LOGGER.info("Checking for duplicate email: {}", dto.getEmail());

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            LOGGER.error("User with email {} already exists", dto.getEmail());
            throw new AppObjectAlreadyExistsException("User", "User with email " + dto.getEmail()
                    + " already exists");
        }

        if (userRepository.findByVat(dto.getVat()).isPresent()) {
            LOGGER.error("User with VAT {} already exists", dto.getVat());
            throw new AppObjectAlreadyExistsException("User", "User with vat " + dto.getVat()
                    + " already exists");
        }

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new AppObjectAlreadyExistsException("User", "User with username " + dto.getUsername()
                    + " already exists");
        }

        if (userRepository.findByPhone(dto.getPhone()).isPresent()) {
            throw new AppObjectAlreadyExistsException("User", "User with phone number " + dto.getPhone()
                    + " already exists");
        }


        LOGGER.info("Mapping DTO to entity: {}", dto);
        User user = mapper.mapToUserEntity(dto);
        LOGGER.info("Saving user to database...");

        try {
            LOGGER.info("Saving user: {}", user);

            User savedUser = userRepository.save(user);
            LOGGER.info("Mapping saved user to read-only DTO...");
            return mapper.mapToUserReadOnlyDTO(savedUser);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Data integrity violation while saving user: {}", user, e);
            throw new AppObjectAlreadyExistsException("User", "Duplicate user data found");
        }
    }

    public UserReadOnlyDTO toggleUserStatus(String username) throws AppObjectNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppObjectNotFoundException("User", "User with username: " + username + " not found"));

        user.setIsActive(!user.getIsActive());
        User updatedUser = userRepository.save(user);
        return mapper.mapToUserReadOnlyDTO(updatedUser);
    }

    @Transactional
    public UserReadOnlyDTO changeUserRole(String username, Role newRole) throws AppObjectNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppObjectNotFoundException("User", "User with username: " + username + " not found"));

        user.setRole(newRole);
        User updatedUser = userRepository.save(user);
        return mapper.mapToUserReadOnlyDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(String username) throws AppObjectNotFoundException {
        LOGGER.info("Attempting to delete user with username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppObjectNotFoundException("User", "User with username: " + username + " not found"));

        userRepository.delete(user);
        LOGGER.info("User with username {} successfully deleted", username);
    }

    @Transactional
    public Page<UserReadOnlyDTO> getPaginatedUsers(int page, int size) {
        String defaultSort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(defaultSort).ascending());
        return userRepository.findAll(pageable).map(mapper::mapToUserReadOnlyDTO);
    }

    @Transactional
    public Paginated<UserReadOnlyDTO> getUsersFilteredPaginated(UserFilters filters) {
        var filtered = userRepository.findAll(getSpecsFromFilters(filters), filters.getPageable());
        return new Paginated<>(filtered.map(mapper::mapToUserReadOnlyDTO));
    }

    private Specification<User> getSpecsFromFilters(UserFilters filters) {
        return Specification
                .where(UserSpecification.stringFieldLike("uuid", filters.getUuid()))
                .and(UserSpecification.userVatIs(filters.getVat()))
                .and(UserSpecification.userIsActive(filters.getIsActive()));
    }


}
