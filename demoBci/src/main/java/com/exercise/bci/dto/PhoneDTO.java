package com.exercise.bci.dto;

import com.exercise.bci.entity.Phone;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhoneDTO {
    private Long number;
    @JsonProperty("citycode")
    private Integer cityCode;
    @JsonProperty("countrycode")
    private String countryCode;

    public static PhoneDTO convertFrom(Phone phoneEntity) {
        return new PhoneDTO(phoneEntity.getNumber(), phoneEntity.getCityCode(), phoneEntity.getCountryCode());
    }
}
