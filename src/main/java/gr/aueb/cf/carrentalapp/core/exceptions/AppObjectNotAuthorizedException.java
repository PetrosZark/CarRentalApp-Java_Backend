package gr.aueb.cf.carrentalapp.core.exceptions;

/**
 * Exception thrown when a user attempts to perform an operation they are not authorized for.
 * This typically occurs during permission checks or role-based access control (RBAC) violations.
 * Extends {@link AppObjectInvalidArgumentException}
 * to represent authorization errors.
 */
public class AppObjectNotAuthorizedException extends AppObjectInvalidArgumentException {

  /**
   * Default suffix for the error code to indicate lack of authorization.
   */
  private static final String DEFAULT_CODE = "Not authorized";

  /**
   * Constructs a new AppObjectNotAuthorizedException with a specific code and message.
   *
   * @param code    The error code representing the specific error type.
   * @param message The detailed error message describing the authorization failure.
   */
  public AppObjectNotAuthorizedException(String code, String message) {
    super(code + DEFAULT_CODE, message);
  }

}
