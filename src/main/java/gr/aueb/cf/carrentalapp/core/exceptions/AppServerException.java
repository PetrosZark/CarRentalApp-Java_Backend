package gr.aueb.cf.carrentalapp.core.exceptions;

import lombok.Getter;

/**
 * Exception representing internal server errors or unexpected failures.
 * This exception is typically thrown when the application encounters an issue that
 * cannot be handled gracefully.
 * Extends {@link java.lang.Exception}.
 */
@Getter
public class AppServerException extends Exception {

    /**
     * Error code that categorizes the type of server error.
     */
    private final String code;

    /**
     * Constructs a new AppServerException with the specified error code and message.
     *
     * @param code    The error code representing the specific error type.
     * @param message The detailed error message describing the server issue.
     */
    public AppServerException(String code, String message) {
        super(message);
        this.code = code;
    }
}
