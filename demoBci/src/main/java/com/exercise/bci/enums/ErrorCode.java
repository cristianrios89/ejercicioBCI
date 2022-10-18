package com.exercise.bci.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    UNKNOWN_ERROR_CODE(999, "Unknown error occurred"),
    INVALID_DATA_ERROR_CODE(1000, "Invalid data in request"),
    USER_ALREADY_EXISTS_ERROR_CODE(1001, "User already exists");

    private Integer errorCode;
    private String description;

    private ErrorCode(Integer code, String description) {
        this.errorCode = code;
        this.description = description;
    }
}
