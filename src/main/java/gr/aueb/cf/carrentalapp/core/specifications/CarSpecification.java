package gr.aueb.cf.carrentalapp.core.specifications;

import gr.aueb.cf.carrentalapp.model.Car;
import gr.aueb.cf.carrentalapp.model.User;
import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import gr.aueb.cf.carrentalapp.model.static_data.CarModel;
import gr.aueb.cf.carrentalapp.model.static_data.City;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specification class providing static methods to build dynamic query conditions for the {@link Car} entity.
 * This class helps filter cars based on various attributes such as brand, model, city, and availability.
 */
public class CarSpecification {

    /**
     * Private constructor to prevent instantiation.
     * This class is designed to provide static methods only.
     */
    private CarSpecification() {

    }

    /**
     * Filters cars by license plate.
     *
     * @param licensePlate The license plate to filter by.
     * @return A {@link Specification} that filters cars matching the license plate.
     */
    public static Specification<Car> carLicensePlateIs(String licensePlate) {
        return (root, query, criteriaBuilder) -> {
            if (licensePlate == null || licensePlate.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("licensePlate"), licensePlate);
        };
    }

    /**
     * Filters cars by brand.
     *
     * @param brand The brand to filter by.
     * @return A {@link Specification} that filters cars matching the brand.
     */
    public static Specification<Car> carBrandIs(Brand brand) {
        return (root, query, criteriaBuilder) -> {
            if (brand == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            return criteriaBuilder.equal(root.get("brand"), brand);
        };
    }

    /**
     * Filters cars by model.
     *
     * @param carModel The model to filter by.
     * @return A {@link Specification} that filters cars matching the model.
     */
    public static Specification<Car> carModelIs(CarModel carModel) {
        return (root, query, criteriaBuilder) -> {
            if (carModel == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            return criteriaBuilder.equal(root.get("carmodel"), carModel);
        };
    }

    /**
     * Filters cars by city.
     *
     * @param city The city to filter by.
     * @return A {@link Specification} that filters cars located in the specified city.
     */
    public static Specification<Car> carCityIs(City city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("city"), city);
        };
    }

    /**
     * Filters cars by their availability status.
     *
     * @param available Boolean indicating car availability.
     * @return A {@link Specification} that filters cars based on availability.
     */
    public static Specification<Car> carAvailabilityIs(Boolean available) {
        return (root, query, criteriaBuilder) -> {
            if (available == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("available"), available);
        };
    }

    /**
     * Excludes cars owned by a specific user.
     *
     * @param user The user to exclude cars for.
     * @return A {@link Specification} that filters out cars belonging to the specified user.
     */
    public static Specification<Car> userIsNot(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("user"), user);
    }

    /**
     * Filters cars by the active status of their associated user.
     *
     * @param isActive Boolean indicating if the user is active.
     * @return A {@link Specification} that filters cars based on the active status of their owner.
     */
    public static Specification<Car> userIsActive(Boolean isActive) {
        return (root, query, criteriaBuilder) -> {
            if (isActive == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));  // No filter
            }
            return criteriaBuilder.equal(root.join("user").get("isActive"), isActive);
        };
    }
}
