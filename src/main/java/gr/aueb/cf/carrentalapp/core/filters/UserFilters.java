package gr.aueb.cf.carrentalapp.core.filters;

import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserFilters extends GenericFilters {

    @Nullable
    private String vat;

    @Nullable
    private Boolean isActive;

    @Nullable
    private Pageable pageable;

}