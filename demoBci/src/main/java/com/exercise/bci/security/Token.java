package com.exercise.bci.security;

import java.util.Map;

public interface Token {
    String generate(Map<String, String> claims);
    Boolean isValid();
}
