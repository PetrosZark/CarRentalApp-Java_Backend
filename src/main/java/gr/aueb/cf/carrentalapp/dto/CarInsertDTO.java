package gr.aueb.cf.carrentalapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for inserting a new car into the system.
 * This class ensures proper validation of car attributes during the insertion process.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarInsertDTO {

    /**
     * License plate of the car.
     * The pattern enforces the format of three uppercase Greek letters followed by four digits.
     */
    @Pattern(regexp = "^[ABEHIKMNOPTXYZ]{3}\\d{4}$", message = "Invalid license plate.")
    private String licensePlate;

    /**
     * Manufacturing year of the car.
     * Must be exactly four digits (e.g., 2023).
     */
    @Pattern(regexp = "^\\d{4}$")
    private String year;

    /**
     * Mileage of the car.
     * Accepts up to 7 digits for the mileage value.
     */
    @Pattern(regexp = "^\\d{1,7}$")
    private String mileage;

    /**
     * ID of the city where the car is located.
     * This field is required and cannot be null.
     */
    @NotNull(message = "City cannot be empty.")
    private Long cityId;

    /**
     * ID of the car's brand.
     * This field is required and cannot be null.
     */
    @NotNull(message = "Brand id cannot be null.")
    private Long brandId;

    /**
     * ID of the car's model.
     * This field is required and cannot be null.
     */
    @NotNull(message = "Car model cannot be null.")
    private Long carmodelId;

    /**
     * Availability status of the car.
     * True if available, false otherwise.
     */
    private Boolean available;
}
