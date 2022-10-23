package com.exercise.bci.exceptions;

import com.exercise.bci.enums.ErrorCode;

public class InvalidAuthenticationCredentialsException extends Exception{

    private static final ErrorCode code = ErrorCode.INVALID_AUTHENTICATION_CREDENTIALS;

    public InvalidAuthenticationCredentialsException(String errorMessage) {
        super(errorMessage);
    }

    public Integer getErrorCode() {
        return code.getErrorCode();
    }
}
