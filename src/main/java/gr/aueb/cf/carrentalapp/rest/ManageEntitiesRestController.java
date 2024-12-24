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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home/manage-entities")
@RequiredArgsConstructor
public class ManageEntitiesRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageEntitiesRestController.class);
    private final UserService userService;
    private final BrandService brandService;
    private final CarModelService carModelService;
    private final CityService cityService;

    @GetMapping("/users")
    public ResponseEntity<Page<UserReadOnlyDTO>> getPaginatedUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserReadOnlyDTO> users = userService.getPaginatedUsers(page, size);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/filtered")
    public ResponseEntity<Paginated<UserReadOnlyDTO>> getFilteredUsers(UserFilters filters) {
        Paginated<UserReadOnlyDTO> filteredUsers = userService.getUsersFilteredPaginated(filters);
        return ResponseEntity.ok(filteredUsers);
    }

    @PatchMapping("/users/{username}/toggle-status")
    public ResponseEntity<UserReadOnlyDTO> toggleUserStatus(@PathVariable String username)
            throws AppObjectNotFoundException {

            UserReadOnlyDTO userReadOnlyDTO = userService.toggleUserStatus(username);
            return ResponseEntity.ok(userReadOnlyDTO);
    }

    @PatchMapping("/users/{username}/change-role")
    public ResponseEntity<UserReadOnlyDTO> changeUserRole (
            @PathVariable String username, @RequestParam Role role) throws AppObjectNotFoundException {
            UserReadOnlyDTO userReadOnlyDTO = userService.changeUserRole(username, role);
            return new ResponseEntity<>(userReadOnlyDTO, HttpStatus.OK);
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) throws AppObjectNotFoundException {
            userService.deleteUser(username);
            LOGGER.info("User with username {} deleted successfully", username);
            return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/brands")
    public ResponseEntity<BrandReadOnlyDTO> createBrand(@RequestBody @Valid BrandInsertDTO dto, BindingResult bindingResult)
            throws ValidationException, AppObjectAlreadyExistsException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
            BrandReadOnlyDTO brandReadOnlyDTO = brandService.saveBrand(dto);
            return new ResponseEntity<>(brandReadOnlyDTO, HttpStatus.CREATED);
    }

    @PostMapping("/carmodels")
    public ResponseEntity<CarModelReadOnlyDTO> createCarModel(@RequestBody @Valid CarModelInsertDTO dto, BindingResult bindingResult)
            throws ValidationException, AppObjectInvalidArgumentException, AppObjectAlreadyExistsException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        CarModelReadOnlyDTO carModelReadOnlyDTO = carModelService.saveCarModel(dto);
        return new ResponseEntity<>(carModelReadOnlyDTO, HttpStatus.CREATED);

    }

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

