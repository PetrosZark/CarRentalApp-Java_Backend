package gr.aueb.cf.carrentalapp.core.specifications;

import gr.aueb.cf.carrentalapp.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    private UserSpecification() {

    }

    public static Specification<User> userVatIs(String vat) {
        return ((root, query, criteriaBuilder) -> {
            if (vat == null || vat.isBlank())
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));

            return criteriaBuilder.equal(root.get("vat"), vat);
        });
    }

    public static Specification<User> userIsActive(Boolean isActive) {
        return (root, query, criteriaBuilder) -> {
            if (isActive == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            return criteriaBuilder.equal(root.get("isActive"), isActive);
        };
    }

    public static Specification<User> stringFieldLike(String field, String value) {
        return (root, query, criteriaBuilder) -> {
            if (value == null || value.trim().isEmpty())
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            return criteriaBuilder.like(criteriaBuilder.upper(root.get(field)), "%" + value.toUpperCase() + "%");
        };
    }
}
