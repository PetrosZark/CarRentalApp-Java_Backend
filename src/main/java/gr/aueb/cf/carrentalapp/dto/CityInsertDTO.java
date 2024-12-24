package gr.aueb.cf.carrentalapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for inserting a new city into the system.
 * This class ensures that city data is properly validated during insertion.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityInsertDTO {

    /**
     * The name of the city to be inserted.
     * This field is required and cannot be null or empty.
     */
    @NotNull(message = "City name cannot be empty")
    private String city;
}
