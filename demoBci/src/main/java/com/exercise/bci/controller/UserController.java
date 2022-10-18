package com.exercise.bci.controller;

import com.exercise.bci.dto.RequestDTO;
import com.exercise.bci.dto.ResponseDTO;
import com.exercise.bci.exceptions.InvalidDataException;
import com.exercise.bci.exceptions.UserAlreadyExistsException;
import com.exercise.bci.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody RequestDTO req) throws InvalidDataException, UserAlreadyExistsException {
        ResponseDTO userCreated = service.CreateUser(req);
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }
}
