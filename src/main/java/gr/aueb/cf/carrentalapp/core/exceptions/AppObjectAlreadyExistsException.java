package gr.aueb.cf.carrentalapp.core.exceptions;

/**
 * Exception thrown when an object that is being created or added already exists in the system.
 * Extends {@link AppGenericException} to provide a standardized structure for exception handling.
 */
public class AppObjectAlreadyExistsException extends AppGenericException {

    /**
     * Default suffix for the error code to indicate existence conflicts.
     */
    private static final String DEFAULT_CODE = " Already exists";

    /**
     * Constructs a new AppObjectAlreadyExistsException with a specific code and message.
     *
     * @param code    The error code representing the specific error type.
     * @param message The detailed error message describing the conflict.
     */
    public AppObjectAlreadyExistsException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
