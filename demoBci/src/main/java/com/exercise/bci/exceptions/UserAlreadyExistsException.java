package com.exercise.bci.exceptions;

import com.exercise.bci.enums.ErrorCode;
import lombok.Getter;

public class UserAlreadyExistsException extends Exception {

    private static final ErrorCode code = ErrorCode.USER_ALREADY_EXISTS_ERROR_CODE;

    public UserAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

    public Integer getErrorCode() {
        return code.getErrorCode();
    }
}
