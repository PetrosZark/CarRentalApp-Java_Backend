package gr.aueb.cf.carrentalapp.rest;

import gr.aueb.cf.carrentalapp.core.enums.Role;
import gr.aueb.cf.carrentalapp.core.exceptions.*;
import gr.aueb.cf.carrentalapp.core.filters.Paginated;
import gr.aueb.cf.carrentalapp.core.filters.UserFilters;
import gr.aueb.cf.carrentalapp.dto.*;
import gr.aueb.cf.carrentalapp.service.BrandService;
import gr.aueb.cf.carrentalapp.service.CarModelService;
import gr.aueb.cf.carrentalapp.service.CityService;
import gr.aueb.cf.carrentalapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing entities such as users, brands, car models, and cities.
 * Provides endpoints for CRUD operations and administrative tasks.
 */
@RestController
@RequestMapping("/api/home/manage-entities")
@SecurityRequirement(name = "Bearer Authentication") // Global security for all endpoints
@RequiredArgsConstructor
public class ManageEntitiesRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageEntitiesRestController.class);
    private final UserService userService;
    private final BrandService brandService;
    private final CarModelService carModelService;
    private final CityService cityService;

    /**
     * Retrieves a paginated list of users, filtered by VAT if provided.
     *
     * @param vat  Optional VAT number for filtering users.
     * @param page Page number for pagination.
     * @param size Number of records per page.
     * @return Paginated list of users.
     */
    @Operation(summary = "Retrieve filtered users with pagination",
            description = "Returns a paginated list of users filtered by VAT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered users retrieved successfully."),
            @ApiResponse(responseCode = "403", description = "Access denied - Requires SUPER_ADMIN role."),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT.")
    })
    @GetMapping("/users")
    public ResponseEntity<Paginated<UserReadOnlyDTO>> getFilteredUsers(
            @RequestParam(required = false) String vat,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        UserFilters filters = new UserFilters();
        filters.setVat(vat);
        filters.setPageable(PageRequest.of(page, size));

        Paginated<UserReadOnlyDTO> filteredUsers = userService.getUsersFilteredPaginated(filters);
        return ResponseEntity.ok(filteredUsers);
    }

    /**
     * Toggles the active status of a user.
     *
     * @param username Username of the user to toggle.
     * @return Updated user details.
     * @throws AppObjectNotFoundException if the user is not found.
     */
    @Operation(summary = "Toggle user active status",
            description = "Enable or disable a user by toggling their status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User status toggled successfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "403", description = "Access denied - Requires SUPER_ADMIN role.")
    })
    @PatchMapping("/users/{username}/toggle-status")
    public ResponseEntity<UserReadOnlyDTO> toggleUserStatus(@PathVariable String username)
            throws AppObjectNotFoundException {

            UserReadOnlyDTO userReadOnlyDTO = userService.toggleUserStatus(username);
            return ResponseEntity.ok(userReadOnlyDTO);
    }

    /**
     * Changes the role of a user.
     *
     * @param username Username of the user whose role will be changed.
     * @param role     New role to assign to the user.
     * @return Updated user details.
     * @throws AppObjectNotFoundException if the user is not found.
     */
    @Operation(summary = "Change user role",
            description = "Updates the role of a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role updated successfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "403", description = "Access denied - Requires SUPER_ADMIN role.")
    })
    @PatchMapping("/users/{username}/change-role")
    public ResponseEntity<UserReadOnlyDTO> changeUserRole (
            @PathVariable String username, @RequestParam Role role) throws AppObjectNotFoundException {
            UserReadOnlyDTO userReadOnlyDTO = userService.changeUserRole(username, role);
            return new ResponseEntity<>(userReadOnlyDTO, HttpStatus.OK);
    }

    /**
     * Deletes a user by username.
     *
     * @param username Username of the user to delete.
     * @return HTTP 200 status upon successful deletion.
     * @throws AppObjectNotFoundException if the user is not found.
     */
    @Operation(summary = "Delete a user",
            description = "Deletes a user by their username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "403", description = "Access denied - Requires SUPER_ADMIN role.")
    })
    @DeleteMapping("/users/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) throws AppObjectNotFoundException {
            userService.deleteUser(username);
            LOGGER.info("User with username {} deleted successfully", username);
            return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * Creates a new brand.
     *
     * @param dto           Brand details.
     * @param bindingResult Validation results.
     * @return Created brand details.
     * @throws ValidationException            if validation fails.
     * @throws AppObjectAlreadyExistsException if the brand already exists.
     */
    @Operation(summary = "Create a new brand",
            description = "Adds a new brand to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand created successfully."),
            @ApiResponse(responseCode = "400", description = "Validation error."),
            @ApiResponse(responseCode = "403", description = "Access denied - Requires SUPER_ADMIN role.")
    })
    @PostMapping("/brands")
    public ResponseEntity<BrandReadOnlyDTO> createBrand(@RequestBody @Valid BrandInsertDTO dto, BindingResult bindingResult)
            throws ValidationException, AppObjectAlreadyExistsException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
            BrandReadOnlyDTO brandReadOnlyDTO = brandService.saveBrand(dto);
            return new ResponseEntity<>(brandReadOnlyDTO, HttpStatus.CREATED);
    }

    /**
     * Creates a new car model and saves it to the database.
     *
     * @param dto           Car model data transfer object (DTO) containing model and brand details.
     * @param bindingResult Validation result for DTO.
     * @return ResponseEntity with created car model details.
     * @throws ValidationException              if the provided data fails validation.
     * @throws AppObjectInvalidArgumentException if the provided arguments are invalid.
     * @throws AppObjectAlreadyExistsException  if the car model already exists.
     */
    @Operation(summary = "Create a new car model",
            description = "Adds a new car model to the system. Requires valid car model and brand details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Car model created successfully."),
            @ApiResponse(responseCode = "400", description = "Validation error or invalid input."),
            @ApiResponse(responseCode = "403", description = "Access denied - Requires SUPER_ADMIN role."),
            @ApiResponse(responseCode = "409", description = "Car model already exists.")
    })@PostMapping("/carmodels")
    public ResponseEntity<CarModelReadOnlyDTO> createCarModel(@RequestBody @Valid CarModelInsertDTO dto, BindingResult bindingResult)
            throws ValidationException, AppObjectInvalidArgumentException, AppObjectAlreadyExistsException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        CarModelReadOnlyDTO carModelReadOnlyDTO = carModelService.saveCarModel(dto);
        return new ResponseEntity<>(carModelReadOnlyDTO, HttpStatus.CREATED);

    }

    /**
     * Creates a new city entry in the database.
     *
     * @param dto           City data transfer object (DTO) containing city details.
     * @param bindingResult Validation result for DTO.
     * @return ResponseEntity with created city details.
     * @throws ValidationException             if the provided data fails validation.
     * @throws AppObjectAlreadyExistsException if the city already exists.
     */
    @Operation(summary = "Create a new city",
            description = "Adds a new city to the system. Requires valid city name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "City created successfully."),
            @ApiResponse(responseCode = "400", description = "Validation error."),
            @ApiResponse(responseCode = "403", description = "Access denied - Requires SUPER_ADMIN role."),
            @ApiResponse(responseCode = "409", description = "City already exists.")
    })
    @PostMapping("/cities")
    public ResponseEntity<CityReadOnlyDTO> createCity(@RequestBody @Valid CityInsertDTO dto, BindingResult bindingResult)
            throws ValidationException, AppObjectAlreadyExistsException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        CityReadOnlyDTO cityReadOnlyDTO = cityService.saveCity(dto);
        return new ResponseEntity<>(cityReadOnlyDTO, HttpStatus.CREATED);

    }
}

