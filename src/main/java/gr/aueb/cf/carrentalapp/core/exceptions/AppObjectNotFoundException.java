package gr.aueb.cf.carrentalapp.core.exceptions;

/**
 * Exception thrown when a requested object is not found in the system.
 * This is typically used in scenarios where an entity lookup fails.
 * Extends {@link AppGenericException}.
 */
public class AppObjectNotFoundException extends AppGenericException {

    /**
     * Default suffix for the error code to indicate a "not found" scenario.
     */
    private static final String DEFAULT_CODE = "Not found";

    /**
     * Constructs a new AppObjectNotFoundException with a specific code and message.
     *
     * @param code    The error code representing the specific error type.
     * @param message The detailed error message describing the missing object.
     */
    public AppObjectNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
