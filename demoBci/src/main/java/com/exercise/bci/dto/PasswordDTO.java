package com.exercise.bci.dto;

import com.exercise.bci.validator.PatternValidator;
import lombok.Data;

@Data
public class PasswordDTO extends PatternValidator {

    private String password;
    private static final String regexPattern = "^(?=.*[a-z])(?=.*[A-Z]\\w{1})(?=.*\\d{1})[a-zA-Z\\d]{8,12}$";

    public String getRegex() {
        return regexPattern;
    };
    
    public String getValue() {
        return password;
    };
}
