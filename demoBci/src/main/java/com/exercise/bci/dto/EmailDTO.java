package com.exercise.bci.dto;

import com.exercise.bci.validator.PatternValidator;

import lombok.Data;

@Data
public class EmailDTO extends PatternValidator {

    private String email;
    private static final String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
            "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public String getRegex() {
        return regexPattern;
    };
    
    public String getValue() {
        return email;
    };
}
