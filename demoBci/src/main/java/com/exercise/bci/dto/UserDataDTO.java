package com.exercise.bci.dto;

import com.exercise.bci.serialization.CustomEmailDeserializer;
import com.exercise.bci.serialization.CustomEmailSerializer;
import com.exercise.bci.serialization.CustomPasswordDeserializer;
import com.exercise.bci.serialization.CustomPasswordSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDTO {
    private String name;
    @JsonDeserialize(using = CustomEmailDeserializer.class)
    @JsonSerialize(using = CustomEmailSerializer.class)
    private EmailDTO email;
    @JsonDeserialize(using = CustomPasswordDeserializer.class)
    @JsonSerialize(using = CustomPasswordSerializer.class)
    private PasswordDTO password;
    private List<PhoneDTO> phones;
}
