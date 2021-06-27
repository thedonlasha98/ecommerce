package com.ecommerce.web.exception;

public class GeneralException extends RuntimeException {
    ErrorCode errorCode;

    public GeneralException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
