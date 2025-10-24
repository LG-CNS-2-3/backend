package com.lgcns.backend_map.core.exception;

public abstract class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    abstract String getErrorCode();
}
