package gr.aueb.cf.carrentalapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for inserting a new car model into the system.
 * This class ensures validation of required fields during the car model creation process.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarModelInsertDTO {

    /**
     * The name of the car model.
     * This field is required and cannot be null or empty.
     */
    @NotNull(message = "Car model name cannot be empty.")
    private String carmodel;

    /**
     * The ID of the brand associated with the car model.
     * This field is required and cannot be null.
     */
    @NotNull(message = "Brand id cannot be empty.")
    private Long brandId;
}
