package gr.aueb.cf.carrentalapp.service;

import gr.aueb.cf.carrentalapp.core.filters.CarFilters;
import gr.aueb.cf.carrentalapp.core.filters.Paginated;
import gr.aueb.cf.carrentalapp.core.specifications.CarSpecification;
import gr.aueb.cf.carrentalapp.dto.CarReadOnlyDTO;
import gr.aueb.cf.carrentalapp.mapper.Mapper;
import gr.aueb.cf.carrentalapp.model.Car;
import gr.aueb.cf.carrentalapp.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Service class for handling car-related operations.
 * Provides methods for filtering and retrieving paginated car data.
 */
@Service
@RequiredArgsConstructor
public class CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);
    private final CarRepository carRepository;
    private final Mapper mapper;

    /**
     * Retrieves paginated and filtered cars based on the provided filter criteria.
     * This method applies filtering and pagination to the car data by using specifications.
     * It returns a paginated list of cars that match the filter criteria.
     *
     * @param filters The filter criteria for retrieving cars.
     * @return A paginated list of filtered cars mapped to DTOs.
     */
    @Transactional
    public Paginated<CarReadOnlyDTO> getCarsFilteredPaginated(CarFilters filters) {
        try {
            // Apply specifications and pagination to filter the cars
            var filtered = carRepository.findAll(getSpecsFromFilters(filters), filters.getPageable());
            // Map the result to DTOs and return as paginated data
            return new Paginated<>(filtered.map(mapper::mapToCarReadOnlyDTO));
        } catch (Exception e) {
            // Log and rethrow any exception that occurs during the process
            LOGGER.error("Error in getCarsFilteredPaginated", e);
            throw e;
        }
    }

    /**
     * Builds a dynamic JPA specification based on the provided car filters.
     * This method constructs a specification for querying cars by chaining multiple
     * filtering conditions based on the filter values provided.
     *
     * @param filters The car filters containing criteria for querying cars.
     * @return A specification for querying cars.
     */
    private Specification<Car> getSpecsFromFilters(CarFilters filters) {
        Specification<Car> specs = Specification.where(null);

        // Filter by car brand
        if (filters.getBrand() != null) {
            specs = specs.and(CarSpecification.carBrandIs(filters.getBrand()));
        }

        // Filter by car model
        if (filters.getCarmodel() != null) {
            specs = specs.and(CarSpecification.carModelIs(filters.getCarmodel()));
        }

        // Filter by license plate
        if (filters.getLicensePlate() != null) {
            specs = specs.and(CarSpecification.carLicensePlateIs(filters.getLicensePlate()));
        }

        // Filter by city
        if (filters.getCity() != null) {
            specs = specs.and(CarSpecification.carCityIs(filters.getCity()));
        }

        // Filter by availability
        if (filters.getAvailable() != null) {
            specs = specs.and(CarSpecification.carAvailabilityIs(filters.getAvailable()));
        }

        // Exclude cars belonging to the specified user
        if (filters.getUser() != null) {
            specs = specs.and(CarSpecification.userIsNot(filters.getUser()));
        }

        // Always filter for cars where the user is active
        specs = specs.and(CarSpecification.userIsActive(true));

        return specs;
    }


}

