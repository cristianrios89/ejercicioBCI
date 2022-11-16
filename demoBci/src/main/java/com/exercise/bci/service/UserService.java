package com.exercise.bci.service;

import com.exercise.bci.dto.LoginResponseDTO;
import com.exercise.bci.dto.UserDataDTO;
import com.exercise.bci.dto.UserMetadataDTO;
import com.exercise.bci.exceptions.*;

public interface UserService {

    UserMetadataDTO createUser(UserDataDTO req) throws InvalidDataException, UserAlreadyExistsException, UnexpectedException;
    String getUsernameFrom(String authHeader) throws InvalidAuthenticationCredentialsException;
    LoginResponseDTO getUserInfo(String username) throws UserNotFoundException, UnexpectedException;
}
