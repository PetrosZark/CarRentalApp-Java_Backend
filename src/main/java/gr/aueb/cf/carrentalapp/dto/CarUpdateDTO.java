package gr.aueb.cf.carrentalapp.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for updating car information.
 * This class is used to capture and validate updated car attributes during update requests.
 * Only mileage, city and availability can be updated.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarUpdateDTO {

    /**
     * Updated mileage of the car.
     * Must be between 1 and 7 digits.
     */
    @Pattern(regexp = "^\\d{1,7}$")
    private String mileage;

    /**
     * The updated city ID where the car is located.
     */
    private Long cityId;

    /**
     * Updated availability status of the car.
     * True if the car is available, false otherwise.
     */
    private Boolean available;

}
