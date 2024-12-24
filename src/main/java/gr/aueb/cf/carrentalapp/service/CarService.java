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

@Service
@RequiredArgsConstructor
public class CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);
    private final CarRepository carRepository;
    private final Mapper mapper;

    @Transactional
    public Paginated<CarReadOnlyDTO> getCarsFilteredPaginated(CarFilters filters) {
        try {
            var filtered = carRepository.findAll(getSpecsFromFilters(filters), filters.getPageable());
            return new Paginated<>(filtered.map(mapper::mapToCarReadOnlyDTO));
        } catch (Exception e) {
            LOGGER.error("Error in getCarsFilteredPaginated", e);
            throw e;
        }
    }

    private Specification<Car> getSpecsFromFilters(CarFilters filters) {
        Specification<Car> specs = Specification.where(null);

        if (filters.getUuid() != null) {
            specs = specs.and(CarSpecification.stringFieldLike("uuid", filters.getUuid()));
        }

        if (filters.getBrand() != null) {
            specs = specs.and(CarSpecification.carBrandIs(filters.getBrand()));
        }

        if (filters.getCarmodel() != null) {
            specs = specs.and(CarSpecification.carModelIs(filters.getCarmodel()));
        }

        if (filters.getLicensePlate() != null) {
            specs = specs.and(CarSpecification.carLicensePlateIs(filters.getLicensePlate()));
        }

        if (filters.getCity() != null) {
            specs = specs.and(CarSpecification.carCityIs(filters.getCity()));
        }

        if (filters.getAvailable() != null) {
            specs = specs.and(CarSpecification.carAvailabilityIs(filters.getAvailable()));
        }

        if (filters.getUser() != null) {
            specs = specs.and(CarSpecification.userIsNot(filters.getUser()));
        }

        specs = specs.and(CarSpecification.userIsActive(true));

        return specs;
    }


}

