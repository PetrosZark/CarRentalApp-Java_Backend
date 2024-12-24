package gr.aueb.cf.carrentalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a read-only view of a car model.
 * This class is used to return car model information to the client without exposing sensitive details.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarModelReadOnlyDTO {

    /**
     * The unique identifier of the car model.
     */
    private Long id;

    /**
     * The name of the car model.
     */
    private String carmodel;

    /**
     * The brand name associated with the car model.
     */
    private String brand;
}
