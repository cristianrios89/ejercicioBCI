package com.exercise.bci.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@JsonRootName(value = "error")
@Data
public class ErrorResponseDTO {

    private Timestamp timestamp;
    @JsonProperty("codigo")
    private Integer code;
    private String detail;

    public ErrorResponseDTO(Integer errorCode, String description) {
        this.timestamp = Timestamp.from(Instant.now());
        this.code = errorCode;
        this.detail = description;
    }
}
