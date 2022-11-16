package com.exercise.bci.controller;

import com.exercise.bci.dto.LoginResponseDTO;
import com.exercise.bci.dto.UserDataDTO;
import com.exercise.bci.dto.UserMetadataDTO;
import com.exercise.bci.exceptions.*;
import com.exercise.bci.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/sign-up")
    public ResponseEntity<UserMetadataDTO> createUser(@RequestBody UserDataDTO req)
            throws InvalidDataException, UserAlreadyExistsException, UnexpectedException {
        UserMetadataDTO userCreated = service.createUser(req);
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization)
            throws InvalidAuthenticationCredentialsException, UserNotFoundException, UnexpectedException {
        String username = service.getUsernameFrom(authorization);
        LoginResponseDTO response = service.getUserInfo(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
