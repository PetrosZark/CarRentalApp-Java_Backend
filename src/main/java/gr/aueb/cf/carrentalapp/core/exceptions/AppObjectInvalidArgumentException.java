package gr.aueb.cf.carrentalapp.core.exceptions;

/**
 * Exception thrown when an invalid argument is provided to an operation.
 * This typically indicates that the input does not meet expected validation rules or constraints.
 * Extends {@link AppGenericException}
 * to provide a standardized structure for handling input errors.
 */
public class AppObjectInvalidArgumentException extends AppGenericException {

  /**
   * Default suffix for the error code to indicate an invalid argument.
   */
  private static final String DEFAULT_CODE = "Invalid argument";

  /**
   * Constructs a new AppObjectInvalidArgumentException with a specific code and message.
   *
   * @param code    The error code representing the specific error type.
   * @param message The detailed error message describing the invalid argument.
   */
  public AppObjectInvalidArgumentException(String code, String message) {
    super(code + DEFAULT_CODE, message);
  }
}
