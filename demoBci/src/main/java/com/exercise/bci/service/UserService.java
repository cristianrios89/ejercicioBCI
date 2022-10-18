package com.exercise.bci.service;

import com.exercise.bci.dto.RequestDTO;
import com.exercise.bci.dto.ResponseDTO;
import com.exercise.bci.exceptions.InvalidDataException;
import com.exercise.bci.exceptions.UserAlreadyExistsException;

public interface UserService {

    ResponseDTO CreateUser(RequestDTO req) throws InvalidDataException, UserAlreadyExistsException;
}
