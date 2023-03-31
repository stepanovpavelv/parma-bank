package parma.edu.money_transfer.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static parma.edu.money_transfer.exception.BankingException.BadRequestException;
import static parma.edu.money_transfer.exception.BankingException.UnauthorizedException;
import static parma.edu.money_transfer.exception.BankingException.ForbiddenException;
import static parma.edu.money_transfer.exception.BankingException.NotFoundException;

import java.time.ZonedDateTime;
import java.util.Arrays;

@ControllerAdvice
public class BankResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Value("${bank-exception.concat-length}")
    private Integer stackTraceLength;

    @ExceptionHandler(value = { BadRequestException.class,
                                UnauthorizedException.class,
                                ForbiddenException.class,
                                NotFoundException.class })
    protected ResponseEntity<Object> handleRequest(BankingException ex, WebRequest request) {
        BankExceptionResponse body = createResponse(ex, request);
        return handleExceptionInternal(ex, body, new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleInternalError(Exception ex, WebRequest request) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        BankExceptionResponse body = createResponse(ex, status.value(), null, request);
        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    private BankExceptionResponse createResponse(BankingException ex, WebRequest req) {
        return createResponse(ex, ex.getHttpStatus().value(), ex.getParams(), req);
    }

    private BankExceptionResponse createResponse(Exception ex, int httpStatus, Object[] params, WebRequest req) {
        String debugMessage = Arrays.toString(ex.getStackTrace());
        if (StringUtils.hasLength(debugMessage)) {
            debugMessage = debugMessage.substring(0, Integer.min(stackTraceLength, debugMessage.length()));
        }

        var exBuilder = BankExceptionResponse.builder()
                .timestamp(ZonedDateTime.now())
                .status(httpStatus)
                .message(ex.getMessage())
                .debugMessage(debugMessage)
                .path(((ServletWebRequest)req).getRequest().getRequestURI());

        if (params != null && params.length > 0) {
            exBuilder.requestBodyText(Arrays.toString(params));
        }

        return exBuilder.build();
    }
}