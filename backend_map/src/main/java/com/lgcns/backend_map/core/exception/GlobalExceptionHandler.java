package com.lgcns.backend_map.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceUnavailableException.class)
    public ErrorResDTO handle(ServiceUnavailableException e){
        return new ErrorResDTO(e.getErrorCode(), e.getMessage());
    }
}
