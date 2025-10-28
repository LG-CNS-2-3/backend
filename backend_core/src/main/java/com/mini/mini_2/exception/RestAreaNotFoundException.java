package com.mini.mini_2.exception;

import static com.mini.mini_2.exception.ErrorCode.*;

public class RestAreaNotFoundException extends NotFoundException{
    public RestAreaNotFoundException(Integer restAreaId) {
        super(REST_AREA_NOT_FOUND_EXCEPTION.getMessage().formatted(restAreaId));
    }

// 2.  String용 생성자 (새로 추가)
    public RestAreaNotFoundException(String identifier) {
        super(REST_AREA_NOT_FOUND_EXCEPTION.getMessage().formatted(identifier));
    }


    @Override
    public String getErrorCode() {
        return REST_AREA_NOT_FOUND_EXCEPTION.name();
    }
}
