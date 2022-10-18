package com.exercise.bci.dto;

import com.exercise.bci.validator.PatternValidator;
import lombok.Data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public String getValueEncrypted() {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(getValue().getBytes());
            byte[] bytes = m.digest();

            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            return s.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
