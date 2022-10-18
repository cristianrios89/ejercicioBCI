package com.exercise.bci.dto;

import com.exercise.bci.serializer.CustomEmailDeserializer;
import com.exercise.bci.serializer.CustomPasswordDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

@Data
public class RequestDTO {
    private String name;
    @JsonDeserialize(using = CustomEmailDeserializer.class)
    private EmailDTO email;
    @JsonDeserialize(using = CustomPasswordDeserializer.class)
    private PasswordDTO password;
    private List<PhoneDTO> phones;
}
