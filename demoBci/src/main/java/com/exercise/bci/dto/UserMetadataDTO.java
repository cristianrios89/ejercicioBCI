package com.exercise.bci.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserMetadataDTO {
    private String id;
    @JsonFormat(pattern="MMM dd, yyyy hh:mm:ss a")
    private LocalDateTime created;
    @JsonFormat(pattern="MMM dd, yyyy hh:mm:ss a")
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;
}
