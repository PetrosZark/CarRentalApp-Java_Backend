package gr.aueb.cf.carrentalapp.core.exceptions;

public class AppObjectInvalidArgumentException extends AppGenericException {

  private static final String DEFAULT_CODE = "Invalid argument";

  public AppObjectInvalidArgumentException(String code, String message) {
    super(code + DEFAULT_CODE, message);
  }
}
