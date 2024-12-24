package gr.aueb.cf.carrentalapp.core.exceptions;

import lombok.Getter;

/**
 * Custom exception class representing a generic application error.
 * This serves as the base exception for other specific exceptions.
 */
@Getter
public class AppGenericException extends Exception {

    /**
     * Error code that categorizes the type of exception.
     */
    public final String code;

    /**
     * Constructs a new AppGenericException with the specified code and message.
     *
     * @param code    The error code representing the specific error type.
     * @param message The detailed error message.
     */
    public AppGenericException(String code, String message) {
        super(message);
        this.code = code;
    }
}
