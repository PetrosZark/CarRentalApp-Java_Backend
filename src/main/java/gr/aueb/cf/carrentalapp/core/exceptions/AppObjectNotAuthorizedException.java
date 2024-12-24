package gr.aueb.cf.carrentalapp.core.exceptions;

public class AppObjectNotAuthorizedException extends AppObjectInvalidArgumentException {

  private static final String DEFAULT_CODE = "Not authorized";

  public AppObjectNotAuthorizedException(String code, String message) {
    super(code + DEFAULT_CODE, message);
  }

}
