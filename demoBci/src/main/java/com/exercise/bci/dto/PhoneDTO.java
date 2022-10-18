package com.exercise.bci.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PhoneDTO {
    private Long number;
    @JsonProperty("citycode")
    private Integer cityCode;
    @JsonProperty("countrycode")
    private String countryCode;
}
