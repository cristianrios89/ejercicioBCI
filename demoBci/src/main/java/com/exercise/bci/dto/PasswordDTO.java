package com.exercise.bci.dto;

import com.exercise.bci.security.Utils;
import com.exercise.bci.validator.PatternValidator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Data
@AllArgsConstructor
public class PasswordDTO extends PatternValidator {
    private String password;
    private static final String regexPattern = "^(?=.*[a-z])(?=.*[A-Z]\\w{1})(?=.*\\d{1})[a-zA-Z\\d]{8,12}$";
    public String getRegex() {
        return regexPattern;
    };
    
    public String getValue() {
        return password;
    };

    public String getValueEncrypted() {
        try {
            return Utils.encrypt(getValue());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
