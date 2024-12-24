package gr.aueb.cf.carrentalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a read-only view of a city.
 * This class is used to return city details to the client without allowing modifications.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityReadOnlyDTO {

    /**
     * The unique identifier of the city.
     */
    private Long id;

    /**
     * The name of the city.
     */
    private String city;
}
