package com.lgcns.backend_map.core.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    EXTERNAL_SERVICE_UNAVAILABLE("외부 API를 이용할 수 없습니다. 나중에 이용하세요");

    private String message;

    ErrorCode(String message){
        this.message = message;
    }
}
