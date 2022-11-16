package com.exercise.bci.exceptions;

import com.exercise.bci.enums.ErrorCode;

public class UnexpectedException extends Exception{

    private static final ErrorCode code = ErrorCode.UNHANDLED_ERROR_CODE;

    public UnexpectedException(String errorMessage) {
        super(errorMessage);
    }

    public Integer getErrorCode() {
        return code.getErrorCode();
    }
}
