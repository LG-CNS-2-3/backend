package com.mini.mini_2.exception;

import static com.mini.mini_2.exception.ErrorCode.*;

public class FacilityNotFoundException extends NotFoundException {
    public FacilityNotFoundException(Integer facilityId) {
        super(FACILITY_NOT_FOUND_EXCEPTION.getMessage().formatted(facilityId));
    }

    @Override
    public String getErrorCode() {
        return FACILITY_NOT_FOUND_EXCEPTION.name();
    }
}
