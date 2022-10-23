package com.exercise.bci.controller;

import com.exercise.bci.dto.ErrorResponseDTO;
import com.exercise.bci.exceptions.InvalidAuthenticationCredentialsException;
import com.exercise.bci.exceptions.InvalidDataException;
import com.exercise.bci.exceptions.UserAlreadyExistsException;
import com.exercise.bci.exceptions.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.exercise.bci.enums.ErrorCode.UNHANDLED_ERROR_CODE;

@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler
{

    public RestControllerAdvice() {
        super();
    }

    @ExceptionHandler( {UserAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserAlreadyExistsException(final UserAlreadyExistsException ex, final WebRequest request) {
        final ErrorResponseDTO bodyOfResponse = new ErrorResponseDTO(ex.getErrorCode(), ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler( {InvalidDataException.class})
    public ResponseEntity<Object> handleInvalidDataException(final InvalidDataException ex, final WebRequest request) {
        final ErrorResponseDTO bodyOfResponse = new ErrorResponseDTO(ex.getErrorCode(), ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler( {InvalidAuthenticationCredentialsException.class})
    public ResponseEntity<Object> handleAuthenticationCredentials(final InvalidAuthenticationCredentialsException ex, final WebRequest request) {
        final ErrorResponseDTO bodyOfResponse = new ErrorResponseDTO(ex.getErrorCode(), ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler( {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(final UserNotFoundException ex, final WebRequest request) {
        final ErrorResponseDTO bodyOfResponse = new ErrorResponseDTO(ex.getErrorCode(), ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler( {Exception.class})
    public ResponseEntity<Object> handleGeneralException(final Exception ex, final WebRequest request) {
        final ErrorResponseDTO bodyOfResponse = new ErrorResponseDTO(UNHANDLED_ERROR_CODE.getErrorCode(), ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
