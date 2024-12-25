package gr.aueb.cf.carrentalapp.core;

import gr.aueb.cf.carrentalapp.dto.ResponseMessageDTO;
import gr.aueb.cf.carrentalapp.core.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * This class centralizes exception handling across all controllers
 * and provides appropriate HTTP responses for different types of exceptions.
 * <p>
 * Exception details are logged for troubleshooting and API response consistency.
 */
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles validation exceptions by extracting field errors from the BindingResult
     * and returning them as a map of field names and corresponding error messages.
     *
     * @param ex The validation exception containing the binding result.
     * @return A ResponseEntity with a map of validation errors and a BAD_REQUEST status.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles cases where an object is not found in the system.
     * <p>
     * Logs the URI path of the request to assist in debugging missing object issues.
     *
     * @param e       The exception thrown when an object is not found.
     * @param request The current web request used to extract request details.
     * @return A ResponseEntity with a {@link ResponseMessageDTO} and NOT_FOUND status.
     */
    @ExceptionHandler({AppObjectNotFoundException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectNotFoundException e, WebRequest request) {

        // Log the request path
        String path = request.getDescription(false);
        System.out.println("Conflict at: " + path);
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles cases where an object already exists in the system.
     * <p>
     * Logs the URI path to help detect duplicate record creation attempts.
     *
     * @param e       The exception thrown when an object already exists.
     * @param request The current web request used to extract request details.
     * @return A ResponseEntity with a {@link ResponseMessageDTO} and CONFLICT status.
     */
    @ExceptionHandler({AppObjectAlreadyExistsException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectAlreadyExistsException e, WebRequest request) {
        String path = request.getDescription(false);
        System.out.println("Conflict at: " + path);
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * Handles cases where invalid arguments are passed to operations.
     * <p>
     * Logs the request URI to trace back to problematic API calls.
     *
     * @param e       The exception thrown when invalid arguments are detected.
     * @param request The current web request to log the request details.
     * @return A ResponseEntity with a {@link ResponseMessageDTO} and BAD_REQUEST status.
     */
    @ExceptionHandler({AppObjectInvalidArgumentException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectInvalidArgumentException e, WebRequest request) {
        String path = request.getDescription(false);
        System.out.println("Conflict at: " + path);
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles authorization failures.
     * <p>
     * This occurs when users attempt unauthorized actions.
     * Logs the request URI for traceability and potential security analysis.
     *
     * @param e       The exception thrown for unauthorized actions.
     * @param request The current web request to log the unauthorized request.
     * @return A ResponseEntity with a {@link ResponseMessageDTO} and FORBIDDEN status.
     */
    @ExceptionHandler({AppObjectNotAuthorizedException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectNotAuthorizedException e, WebRequest request) {String path = request.getDescription(false);  // Get request URI
        System.out.println("Conflict at: " + path);
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.FORBIDDEN);
    }

    /**
     * Handles unexpected internal server errors.
     * <p>
     * This handler is triggered by unhandled application logic errors or server malfunctions.
     * Logs the path of the failed request for further investigation.
     *
     * @param e       The exception indicating a server-side failure.
     * @param request The current web request for context and logging.
     * @return A ResponseEntity with a {@link ResponseMessageDTO} and INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler({AppServerException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppServerException e, WebRequest request) {
        String path = request.getDescription(false);
        System.out.println("Conflict at: " + path);
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
