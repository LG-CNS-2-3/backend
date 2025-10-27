package com.lgcns.backend_map.core.exception;

import static com.lgcns.backend_map.core.exception.ErrorCode.*;

public class ExternalServiceUnavailableException extends ServiceUnavailableException{
    public ExternalServiceUnavailableException() {
        super(EXTERNAL_SERVICE_UNAVAILABLE.getMessage());
    }

    @Override
    public String getErrorCode() {
        return EXTERNAL_SERVICE_UNAVAILABLE.name();
    }
}
