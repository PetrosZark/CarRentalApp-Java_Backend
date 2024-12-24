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

    /**
     * Constructs a ResponseMessageDTO with only a response code.
     * The description is set to an empty string by default.
     *
     * @param code The response code to be sent.
     */
    public ResponseMessageDTO(String code) {
        this.code = code;
        this.description = "";
    }
}
