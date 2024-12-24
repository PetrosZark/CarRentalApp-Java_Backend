package gr.aueb.cf.carrentalapp.core.exceptions;

public class AppObjectNotFoundException extends AppGenericException {

    private static final String DEFAULT_CODE = "Not found";

    public AppObjectNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
