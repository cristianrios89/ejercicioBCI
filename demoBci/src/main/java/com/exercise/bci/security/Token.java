package com.exercise.bci.security;

import com.exercise.bci.exceptions.InvalidAuthenticationCredentialsException;
import com.exercise.bci.exceptions.UnexpectedException;
import io.jsonwebtoken.Claims;

public interface Token {
    String generate(String subject) throws UnexpectedException;
    Claims getClaimsFromToken(String token) throws InvalidAuthenticationCredentialsException;
}
