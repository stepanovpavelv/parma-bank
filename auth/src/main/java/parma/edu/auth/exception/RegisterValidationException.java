package parma.edu.auth.exception;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

public class RegisterValidationException extends RuntimeException {
    @Getter
    private List<ObjectError> errorMessages;

    public RegisterValidationException() {
        super();
    }

    public RegisterValidationException(Throwable e) {
        super(e);
    }

    public RegisterValidationException(String message, Throwable e) {
        super(message, e);
    }

    public RegisterValidationException(String message) {
        super(message);
    }

    public RegisterValidationException(List<ObjectError> errorMessages) {
        super();
        this.errorMessages = errorMessages;
    }
}