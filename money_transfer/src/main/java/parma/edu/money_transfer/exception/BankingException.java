package parma.edu.money_transfer.exception;

import org.springframework.http.HttpStatus;

public abstract class BankingException extends RuntimeException {
    protected Object[] params;

    public abstract HttpStatus getHttpStatus();

    public Object[] getParams() {
        return params;
    }

    public BankingException() {
        super();
    }

    public BankingException(Throwable e) {
        super(e);
    }

    public BankingException(String message) {
        super(message);
    }

    public BankingException(String message, Object... params) {
        super(message);
        this.params = params;
    }

    public BankingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankingException(String errorCode, Object[] args, Throwable cause) {
        this(String.format(errorCode, args), cause);
    }

    public static class BadRequestException extends BankingException {
        public BadRequestException() { }

        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.BAD_REQUEST;
        }

        public BadRequestException(String message) {
            super(message);
        }

        public BadRequestException(String message, Object... param) {
            super(message, param);
        }
    }

    public static class ForbiddenException extends BankingException {
        public ForbiddenException() { }

        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.FORBIDDEN;
        }

        public ForbiddenException(String message) {
            super(message);
        }

        public ForbiddenException(String message, Object... param) {
            super(message, param);
        }
    }

    public static class NotFoundException extends BankingException {
        public NotFoundException() { }

        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.NOT_FOUND;
        }

        public NotFoundException(String message) {
            super(message);
        }

        public NotFoundException(String message, Object... param) {
            super(message, param);
        }
    }

    public static class UnauthorizedException extends BankingException {
        public UnauthorizedException() { }

        @Override
        public HttpStatus getHttpStatus() {
            return HttpStatus.UNAUTHORIZED;
        }

        public UnauthorizedException(String message) {
            super(message);
        }

        public UnauthorizedException(String message, Object... param) {
            super(message, param);
        }
    }
}