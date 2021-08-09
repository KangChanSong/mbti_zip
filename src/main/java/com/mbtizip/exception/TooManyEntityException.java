package com.mbtizip.exception;

public class TooManyEntityException extends RuntimeException{
    public TooManyEntityException() {
    }

    public TooManyEntityException(String message) {
        super(message);
    }

    public TooManyEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyEntityException(Throwable cause) {
        super(cause);
    }

    public TooManyEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
