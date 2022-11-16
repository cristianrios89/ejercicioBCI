package com.exercise.bci.exceptions;

import com.exercise.bci.enums.ErrorCode;

public class UserNotFoundException extends Exception {

    private static final ErrorCode code = ErrorCode.USER_NOT_FOUND_ERROR_CODE;

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public Integer getErrorCode() {
        return code.getErrorCode();
    }
}
