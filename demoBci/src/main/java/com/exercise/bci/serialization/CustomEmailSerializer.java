package com.exercise.bci.serialization;

import com.exercise.bci.dto.EmailDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomEmailSerializer extends StdSerializer<EmailDTO> {

    public CustomEmailSerializer() {
        this(null);
    }

    public CustomEmailSerializer(Class<EmailDTO> t) {
        super(t);
    }

    @Override
    public void serialize(EmailDTO email, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(email.getEmail());
    }
}
