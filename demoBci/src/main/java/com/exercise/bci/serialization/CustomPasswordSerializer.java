package com.exercise.bci.serialization;

import com.exercise.bci.dto.EmailDTO;
import com.exercise.bci.dto.PasswordDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomPasswordSerializer extends StdSerializer<PasswordDTO> {

    public CustomPasswordSerializer() {
        this(null);
    }

    public CustomPasswordSerializer(Class<PasswordDTO> t) {
        super(t);
    }

    @Override
    public void serialize(PasswordDTO email, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(email.getPassword());
    }
}
