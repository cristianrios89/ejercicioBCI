package com.exercise.bci.enums;

import lombok.Getter;

@Getter
public enum Constants {

    BEARER_PREFIX("Bearer "),
    JWT_ISSUER("BCI"),
    USER_ALREADY_EXISTS_ERROR_CODE("User already exists"),
    INVALID_AUTHENTICATION_CREDENTIALS("Invalid credentials");

    private String value;

    private Constants(String v) {
        this.value = v;
    }
}
