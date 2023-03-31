package parma.edu.auth.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import parma.edu.auth.dto.ErrorResponseDto;

import java.util.stream.Collectors;

@ControllerAdvice
public class AuthResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { RegisterValidationException.class })
    protected ResponseEntity<Object> handleRegisterRequest(RegisterValidationException ex, WebRequest request) {
        ErrorResponseDto body = createResponse(ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { UsernameNotFoundException.class })
    protected ResponseEntity<Object> handleNotFoundRequest(Exception ex, WebRequest request) {
        ErrorResponseDto body = createResponse(ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private static ErrorResponseDto createResponse(Exception ex) {
        if (ex instanceof RegisterValidationException) {
            return createRegisterResponse((RegisterValidationException) ex);
        } else {
            return createSimpleResponse(ex);
        }
    }

    private static ErrorResponseDto createRegisterResponse(RegisterValidationException ex) {
        String errorMessage;
        if (ex.getErrorMessages() != null && !ex.getErrorMessages().isEmpty()) {
            errorMessage = ex.getErrorMessages().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("; "));
        } else {
            errorMessage = ex.getLocalizedMessage();
        }

        return new ErrorResponseDto(errorMessage);
    }

    private static ErrorResponseDto createSimpleResponse(Throwable ex) {
        return new ErrorResponseDto(ex.getLocalizedMessage());
    }
}