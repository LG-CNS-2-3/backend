package com.lgcns.backend_map.core.exception;

public abstract class ServiceUnavailableException extends CustomException{
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
