package com.exercise.bci.serializer;

import com.exercise.bci.dto.PasswordDTO;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CustomPasswordDeserializer extends StdDeserializer<PasswordDTO> {

    public CustomPasswordDeserializer() {
        this(null);
    }

    public CustomPasswordDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PasswordDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        PasswordDTO pw = new PasswordDTO();
        pw.setPassword(p.getText());
        return pw;
    }
}
