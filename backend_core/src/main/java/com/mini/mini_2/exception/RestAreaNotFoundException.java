package com.mini.mini_2.exception;

import static com.mini.mini_2.exception.ErrorCode.*;

public class RestAreaNotFoundException extends NotFoundException{
    public RestAreaNotFoundException(Integer restAreaId) {
        super(REST_AREA_NOT_FOUND_EXCEPTION.getMessage().formatted(restAreaId));
    }

    @Override
    public String getErrorCode() {
        return REST_AREA_NOT_FOUND_EXCEPTION.name();
    }
}
