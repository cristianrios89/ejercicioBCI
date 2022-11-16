package com.exercise.bci.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    @JsonUnwrapped
    private UserMetadataDTO userMetadata;
    @JsonUnwrapped
    private UserDataDTO userData;
}
