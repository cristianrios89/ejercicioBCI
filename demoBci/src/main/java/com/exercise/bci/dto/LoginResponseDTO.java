package com.exercise.bci.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class LoginResponseDTO {

    @JsonUnwrapped
    private ResponseDTO userMetadata;
    @JsonUnwrapped
    private RequestDTO userData;
}
