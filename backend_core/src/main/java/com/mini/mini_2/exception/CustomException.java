package com.mini.mini_2.exception;

public abstract class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
    abstract String getErrorCode();
}
