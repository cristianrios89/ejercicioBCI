package com.exercise.bci.exceptions;

import com.exercise.bci.enums.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidDataException extends Exception {

    private static final ErrorCode code = ErrorCode.INVALID_DATA_ERROR_CODE;

    public InvalidDataException(String errorMessage) {
        super(errorMessage);
    }

    public Integer getErrorCode() {
        return code.getErrorCode();
    }
}
