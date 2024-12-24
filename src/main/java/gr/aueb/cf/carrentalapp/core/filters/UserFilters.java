package gr.aueb.cf.carrentalapp.core.filters;

import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

/**
 * Filter class for applying search and filtering criteria to user entities.
 * Extends {@link GenericFilters} to include common pagination and sorting fields.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserFilters extends GenericFilters {

    /**
     * VAT (Value Added Tax) number to filter users by.
     */
    @Nullable
    private String vat;

    /**
     * Filter to retrieve active or inactive users.
     */
    @Nullable
    private Boolean isActive;

    /**
     * Pageable object for handling pagination and sorting of filtered results.
     */
    @Nullable
    private Pageable pageable;

}