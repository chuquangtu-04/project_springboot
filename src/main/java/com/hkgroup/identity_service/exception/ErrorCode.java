package com.hkgroup.identity_service.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error"),
    USER_INVALID(1003, "The username must have at least 3 characters"),
    PASSWORD_INVALID(1004, "The password must have at least 8 characters"),
    INVALID_KEY(2004, "Invalid message key"),
    USER_EXISTED(1001, "User existed!");
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
