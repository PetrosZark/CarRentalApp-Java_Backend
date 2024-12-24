package gr.aueb.cf.carrentalapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) used for inserting a new car brand.
 * This class ensures the brand name is provided and validated during the request.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandInsertDTO {

    /**
     * The name of the brand to be inserted.
     * This field is mandatory and cannot be null or empty.
     */
    @NotNull(message = "Brand name cannot be empty")
    private String brand;
}
