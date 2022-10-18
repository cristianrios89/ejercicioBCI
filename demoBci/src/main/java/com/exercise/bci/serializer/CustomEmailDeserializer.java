package com.exercise.bci.serializer;

import com.exercise.bci.dto.EmailDTO;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CustomEmailDeserializer extends StdDeserializer<EmailDTO> {

    public CustomEmailDeserializer() {
        this(null);
    }

    public CustomEmailDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public EmailDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        EmailDTO email = new EmailDTO();
        email.setEmail(p.getText());
        return email;
    }
}
