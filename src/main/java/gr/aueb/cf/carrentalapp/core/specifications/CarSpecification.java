package gr.aueb.cf.carrentalapp.core.specifications;

import gr.aueb.cf.carrentalapp.model.Car;
import gr.aueb.cf.carrentalapp.model.User;
import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import gr.aueb.cf.carrentalapp.model.static_data.CarModel;
import gr.aueb.cf.carrentalapp.model.static_data.City;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecification {

    private CarSpecification() {

    }

    public static Specification<Car> carLicensePlateIs(String licensePlate) {
        return (root, query, criteriaBuilder) -> {
            if (licensePlate == null || licensePlate.isBlank()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("licensePlate"), licensePlate);
        };
    }

    public static Specification<Car> carBrandIs(Brand brand) {
        return (root, query, criteriaBuilder) -> {
            if (brand == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            return criteriaBuilder.equal(root.get("brand"), brand);
        };
    }

    public static Specification<Car> carModelIs(CarModel carModel) {
        return (root, query, criteriaBuilder) -> {
            if (carModel == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            return criteriaBuilder.equal(root.get("carmodel"), carModel);
        };
    }

    public static Specification<Car> carCityIs(City city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("city"), city);
        };
    }

    public static Specification<Car> stringFieldLike(String field, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null || value.trim().isEmpty())
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            return criteriaBuilder.like(criteriaBuilder.upper(root.get(field)), "%" + value.toUpperCase() + "%");
        };
    }

    public static Specification<Car> carAvailabilityIs(Boolean available) {
        return (root, query, criteriaBuilder) -> {
            if (available == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("available"), available);
        };
    }

    public static Specification<Car> userIsNot(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("user"), user);
    }

    public static Specification<Car> userIsActive(Boolean isActive) {
        return (root, query, criteriaBuilder) -> {
            if (isActive == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));  // No filter
            }
            // Join with the user entity and filter by isActive
            return criteriaBuilder.equal(root.join("user").get("isActive"), isActive);
        };
    }
}
