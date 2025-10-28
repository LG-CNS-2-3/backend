package com.mini.mini_2.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    REST_AREA_NOT_FOUND_EXCEPTION("해당 id 에 해당하는 휴게소가 없습니다. restAreaId = %d");

    private String message;

    ErrorCode(String message){
        this.message = message;
    }
}
