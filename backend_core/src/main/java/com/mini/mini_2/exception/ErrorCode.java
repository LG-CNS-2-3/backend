package com.mini.mini_2.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    REST_AREA_NOT_FOUND_EXCEPTION("해당 id 에 해당하는 휴게소가 없습니다. restAreaId = %d"),
        // --- ⬇️ Food 관련 에러 코드 추가 ⬇️ ---
    FOOD_NOT_FOUND_EXCEPTION("해당 id 에 해당하는 음식이 없습니다. foodId = %d"),

    // --- ⬇️ Facility 관련 에러 코드 추가 ⬇️ ---
    FACILITY_NOT_FOUND_EXCEPTION("해당 id 에 해당하는 편의시설이 없습니다. facilityId = %d"),

    // --- ⬇️ User 관련 에러 코드 추가 (예시) ⬇️ ---
    USER_NOT_FOUND_BY_EMAIL_EXCEPTION("해당 이메일에 해당하는 사용자가 없습니다. email = %s"),
    USER_NOT_FOUND_BY_ID_EXCEPTION("해당 id 에 해당하는 사용자가 없습니다. userId = %d"),
  

    // --- ⬇️ Review, Favorite 관련 에러 코드 추가 (예시) ⬇️ ---
    REVIEW_NOT_FOUND_EXCEPTION("해당 id 에 해당하는 리뷰가 없습니다. reviewId = %d"),
    FAVORITE_NOT_FOUND_EXCEPTION("해당 id 에 해당하는 즐겨찾기가 없습니다. favoriteId = %d");

    private String message;

    ErrorCode(String message){
        this.message = message;
    }
}
