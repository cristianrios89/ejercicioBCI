package com.exercise.bci.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    UNHANDLED_ERROR_CODE(999, "Unhandled error occurred"),
    INVALID_DATA_ERROR_CODE(1000, "Invalid data in request"),
    USER_ALREADY_EXISTS_ERROR_CODE(1001, "User already exists"),
    USER_NOT_FOUND_ERROR_CODE(1002, "User does not exists"),
    INVALID_AUTHENTICATION_CREDENTIALS(1003, "Invalid credentials");

    private Integer errorCode;
    private String description;

    private ErrorCode(Integer code, String description) {
        this.errorCode = code;
        this.description = description;
    }
}
