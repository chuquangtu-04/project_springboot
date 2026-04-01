package com.hkgroup.identity_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_INVALID(1003, "The username must have at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "The password must have at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_KEY(2004, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001, "User existed!", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(1005,"phone invalid format", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(1008, "Token expired", HttpStatus.UNAUTHORIZED),
    PERMISSION_EXISTED(1009, "Permission existed!", HttpStatus.UNAUTHORIZED),
    ROLE_EXISTED(1010, "Role existed!", HttpStatus.CONFLICT),
    INVALID_DOB(2000, "You do must be at least {min}", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1001, "User not existed!", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(1006, "You do not have permission", HttpStatus.FORBIDDEN);
    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private int code;
    private HttpStatus httpStatus;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
