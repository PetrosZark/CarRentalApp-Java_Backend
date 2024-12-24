package gr.aueb.cf.carrentalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a read-only view of a car brand.
 * This class is used to return brand information to the client without exposing sensitive details.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandReadOnlyDTO {

    /**
     * The unique identifier of the brand.
     */
    private Long id;

    /**
     * The name of the brand.
     */
    private String brand;
}
