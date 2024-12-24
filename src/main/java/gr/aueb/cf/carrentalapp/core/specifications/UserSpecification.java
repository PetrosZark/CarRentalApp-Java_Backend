package gr.aueb.cf.carrentalapp.core.specifications;

import gr.aueb.cf.carrentalapp.model.User;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specification class providing static methods to build dynamic query conditions for the {@link User} entity.
 * This class is used to filter users based on VAT and active status.
 */
public class UserSpecification {

    /**
     * Private constructor to prevent instantiation.
     * This class is designed to provide static methods only.
     */
    private UserSpecification() {
    }

    /**
     * Filters users by VAT number.
     *
     * @param vat The VAT number to filter by.
     * @return A {@link Specification} that filters users matching the VAT number (partial match).
     */
    public static Specification<User> userVatIs(String vat) {
        return (root, query, criteriaBuilder) -> {
            if (vat == null || vat.isBlank())
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));

            return criteriaBuilder.like(root.get("vat"), "%" + vat + "%");
        };
    }

    /**
     * Filters users by their active status.
     *
     * @param isActive Boolean indicating if the user is active.
     * @return A {@link Specification} that filters users based on their active status.
     */
    public static Specification<User> userIsActive(Boolean isActive) {
        return (root, query, criteriaBuilder) -> {
            if (isActive == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            return criteriaBuilder.equal(root.get("isActive"), isActive);
        };
    }
}
