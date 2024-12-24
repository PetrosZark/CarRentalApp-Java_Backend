package gr.aueb.cf.carrentalapp.core.exceptions;

public class AppObjectAlreadyExistsException extends AppGenericException {

    private static final String DEFAULT_CODE = " Already exists";

    public AppObjectAlreadyExistsException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
