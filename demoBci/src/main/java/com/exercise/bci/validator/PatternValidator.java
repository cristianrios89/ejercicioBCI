package com.exercise.bci.validator;

import java.util.regex.Pattern;

public abstract class PatternValidator {
    public abstract String getRegex();
    public abstract String getValue();

    public boolean isValid() {
        return Pattern.compile(getRegex())
                .matcher(getValue())
                .matches();
    }
}
