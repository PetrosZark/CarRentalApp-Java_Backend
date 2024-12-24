package gr.aueb.cf.carrentalapp.rest;

import gr.aueb.cf.carrentalapp.core.filters.CarFilters;
import gr.aueb.cf.carrentalapp.core.filters.Paginated;
import gr.aueb.cf.carrentalapp.dto.BrandReadOnlyDTO;
import gr.aueb.cf.carrentalapp.dto.CarModelReadOnlyDTO;
import gr.aueb.cf.carrentalapp.dto.CarReadOnlyDTO;
import gr.aueb.cf.carrentalapp.dto.CityReadOnlyDTO;
import gr.aueb.cf.carrentalapp.mapper.Mapper;
import gr.aueb.cf.carrentalapp.model.User;
import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import gr.aueb.cf.carrentalapp.model.static_data.CarModel;
import gr.aueb.cf.carrentalapp.model.static_data.City;
import gr.aueb.cf.carrentalapp.service.BrandService;
import gr.aueb.cf.carrentalapp.service.CarModelService;
import gr.aueb.cf.carrentalapp.service.CarService;
import gr.aueb.cf.carrentalapp.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for searching cars, brands, models, and cities.
 * Provides endpoints to filter and retrieve cars with specific attributes.
 */
@RestController
@RequestMapping("/api/home/cars")
@SecurityRequirement(name = "Bearer Authentication") // Apply JWT security globally
@RequiredArgsConstructor
public class SearchCarRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchCarRestController.class);
    private final CarService carService;
    private final BrandService brandService;
    private final CityService cityService;
    private final CarModelService carModelService;
    private final Mapper mapper;

    /**
     * Retrieves paginated and filtered cars.
     * Allows filtering by license plate, availability, brand, car model, and city.
     *
     * @param loggedInUser  The authenticated user.
     * @param licensePlate  Optional filter by license plate.
     * @param available     Filter by availability (default: true).
     * @param brandName     Optional filter by brand name.
     * @param carModelName  Optional filter by car model name.
     * @param cityName      Optional filter by city.
     * @param page          Page number (default: 0).
     * @param size          Page size (default: 10).
     * @param sortBy        Sort field (default: id).
     * @param sortDirection Sort direction (ASC/DESC, default: ASC).
     * @return Paginated list of cars.
     */
    @Operation(summary = "Retrieve filtered cars with pagination",
            description = "Returns paginated and filtered cars based on provided filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered cars retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid filter criteria"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Paginated<CarReadOnlyDTO>> getCarsFilteredPaginated(
            @AuthenticationPrincipal User loggedInUser,
            @RequestParam(value = "licensePlate", required = false) String licensePlate,
            @RequestParam(value = "available", required = false) Boolean available,
            @RequestParam(value = "brand", required = false) String brandName,
            @RequestParam(value = "carmodel", required = false) String carModelName,
            @RequestParam(value = "city", required = false) String cityName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        try {
            LOGGER.debug("Received parameters: licensePlate={}, available={}, brand={}, carmodel={}, " +
                            "city={}, page={}, size={}, sortBy={}, sortDirection={}",
                    licensePlate, available, brandName, carModelName, cityName, page, size, sortBy, sortDirection);

            Brand brand = (brandName != null) ? brandService.findByName(brandName) : null;
            CarModel carmodel = (carModelName != null) ? carModelService.findByName(carModelName) : null;
            City city = (cityName != null) ? cityService.findByName(cityName) : null;

            CarFilters filters = CarFilters.builder()
                    .licensePlate(licensePlate)
                    .available(available != null ? available : true)
                    .brand(brand)
                    .carmodel(carmodel)
                    .city(city)
                    .page(page)
                    .pageSize(size)
                    .sortBy(sortBy != null ? sortBy : "id")
                    .sortDirection(sortDirection != null ? sortDirection : Sort.Direction.ASC)
                    .user(loggedInUser)
                    .build();

            LOGGER.debug("CarFilters built: {}", filters);
            Paginated<CarReadOnlyDTO> result = carService.getCarsFilteredPaginated(filters);
            LOGGER.debug("Service returned: totalElements={}, totalPages={}", result.getTotalElements(), result.getTotalPages());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            LOGGER.error("Error fetching cars with filters", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all available car brands.
     *
     * @return List of car brands.
     */
    @Operation(summary = "Retrieve all car brands",
            description = "Returns a list of all available car brands.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car brands retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/brands")
    public ResponseEntity<List<BrandReadOnlyDTO>> getAllBrands() {
        try {
            List<BrandReadOnlyDTO> brands = brandService.getAllBrands()
                    .stream()
                    .map(mapper::mapToBrandReadOnlyDTO)
                    .toList();
            return new ResponseEntity<>(brands, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not fetch brands.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all available car models.
     *
     * @return List of car models.
     */
    @Operation(summary = "Retrieve all car models",
            description = "Returns a list of all available car models.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car models retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/models")
    public ResponseEntity<List<CarModelReadOnlyDTO>> getAllCarModels() {
        try {
            List<CarModelReadOnlyDTO> carModels = carModelService.getAllCarModels()
                    .stream()
                    .map(mapper::mapToCarModelReadOnlyDTO)
                    .toList();
            return new ResponseEntity<>(carModels, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not fetch car models.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all available cities.
     *
     * @return List of cities.
     */
    @Operation(summary = "Retrieve all cities",
            description = "Returns a list of all available cities.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cities retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/cities")
    public ResponseEntity<List<CityReadOnlyDTO>> getAllCities() {
        try {
            List<CityReadOnlyDTO> cities = cityService.getAllCities()
                    .stream()
                    .map(mapper::mapToCityReadOnlyDTO).toList();
            return new ResponseEntity<>(cities, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not fetch cities.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


