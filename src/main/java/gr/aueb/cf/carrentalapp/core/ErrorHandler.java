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
     *
     * @param e       The exception thrown when an object is not found.
     * @param request The current web request.
     * @return A ResponseEntity with a {@link ResponseMessageDTO} and NOT_FOUND status.
     */
    @ExceptionHandler({AppObjectNotFoundException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectNotFoundException e, WebRequest request) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles cases where an object already exists in the system.
     *
     * @param e       The exception thrown when an object already exists.
     * @param request The current web request.
     * @return A ResponseEntity with a {@link ResponseMessageDTO} and CONFLICT status.
     */
    @ExceptionHandler({AppObjectAlreadyExistsException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectAlreadyExistsException e, WebRequest request) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * Handles cases where an invalid argument is passed to an operation.
     *
     * @param e       The exception thrown when invalid arguments are detected.
     * @param request The current web request.
     * @return A ResponseEntity with a {@link ResponseMessageDTO} and BAD_REQUEST status.
     */
    @ExceptionHandler({AppObjectInvalidArgumentException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectInvalidArgumentException e, WebRequest request) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles authorization failures, typically occurring when a user attempts to perform
     * an action they are not authorized to.
     *
     * @param e       The exception thrown when a user is not authorized.
     * @param request The current web request.
     * @return A ResponseEntity with a {@link ResponseMessageDTO} and FORBIDDEN status.
     */
    @ExceptionHandler({AppObjectNotAuthorizedException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectNotAuthorizedException e, WebRequest request) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.FORBIDDEN);
    }

    /**
     * Handles internal server errors that may occur due to unhandled application logic.
     *
     * @param e       The exception thrown during server failure.
     * @param request The current web request.
     * @return A ResponseEntity with a {@link ResponseMessageDTO} and INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler({AppServerException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppServerException e, WebRequest request) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
