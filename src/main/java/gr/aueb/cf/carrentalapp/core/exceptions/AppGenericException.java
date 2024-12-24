package gr.aueb.cf.carrentalapp.core.exceptions;

import lombok.Getter;

@Getter
public class AppGenericException extends Exception {
    public final String code;

    public AppGenericException(String code, String message) {
        super(message);
        this.code = code;
    }
}
