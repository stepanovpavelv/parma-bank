package parma.edu.money_transfer.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * Ответ системы в случае выбрасывания исключения.
 */
@Getter
@Setter
@Builder
public class BankExceptionResponse {
    private ZonedDateTime timestamp;
    private int status;
    private String message;
    private String debugMessage;
    private String path;
    private String requestBodyText;
}