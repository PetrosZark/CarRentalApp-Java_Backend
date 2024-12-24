package gr.aueb.cf.carrentalapp.core.exceptions;

import lombok.Getter;
import org.springframework.validation.BindingResult;

/**
 * Exception thrown when validation of input data fails.
 * This exception is used to encapsulate validation errors that occur
 * during request processing.
 * Extends {@link java.lang.Exception}.
 */
@Getter
public class ValidationException extends Exception {

  /**
   * Holds the result of the validation process, containing error details.
   */
  private final BindingResult bindingResult;

  /**
   * Constructs a new ValidationException with the given BindingResult.
   *
   * @param bindingResult The result of the validation process, including errors.
   */
  public ValidationException(BindingResult bindingResult) {
    super("Validation failed");
    this.bindingResult = bindingResult;
  }
}
