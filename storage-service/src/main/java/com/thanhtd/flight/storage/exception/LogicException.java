package com.thanhtd.flight.storage.exception;

import com.thanhtd.flight.storage.constant.ErrorCode;

public class LogicException extends GlobalException {
    private ErrorCode errorCode;

    public LogicException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
    }

    public LogicException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public LogicException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public LogicException(String message) {
        super(message);
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
