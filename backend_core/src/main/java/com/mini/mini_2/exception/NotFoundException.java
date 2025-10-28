package com.mini.mini_2.exception;

public abstract class NotFoundException extends CustomException {
    public NotFoundException(String message) {
        super(message);
    }
}
