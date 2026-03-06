package com.hkgroup.identity_service.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error"),
    USER_INVALID(1003, "The username must have at least {min} characters"),
    PASSWORD_INVALID(1004, "The password must have at least {min} characters"),
    INVALID_KEY(2004, "Invalid message key"),
    USER_EXISTED(1001, "User existed!"),
    PHONE_INVALID(1005,"phone invalid format"),
    UNAUTHENTICATED(1007, "Unauthenticated"),
    TOKEN_EXPIRED(1008, "Token expired"),
    PERMISSION_EXISTED(1009, "Permission existed!"),
    ROLE_EXISTED(1010, "Role existed!"),
    INVALID_DOB(2000, "You do must be at least {min}"),
    USER_NOT_EXISTED(1001, "User not existed!");
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
