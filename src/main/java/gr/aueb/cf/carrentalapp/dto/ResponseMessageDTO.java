package gr.aueb.cf.carrentalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for sending standardized response messages.
 * This class is used to communicate status codes and descriptive messages to the client.
 */
@Data
@AllArgsConstructor
public class ResponseMessageDTO {

    /**
     * A short code representing the type or category of the response.
     */
    private String code;

    /**
     * A detailed description providing more context about the response.
     */
    private String description;
}
