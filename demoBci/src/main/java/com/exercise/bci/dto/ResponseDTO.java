package com.exercise.bci.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseDTO {
    private String id;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;
}
