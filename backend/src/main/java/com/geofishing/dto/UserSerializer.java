package com.geofishing.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class UserSerializer extends JsonSerializer<FacebookUser> {
    @Override
    public void serialize(FacebookUser value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeStringField("lastname", value.getLastName());
        gen.writeStringField("id", value.getId());
        gen.writeStringField("email", value.getEmail());
        gen.writeEndObject();

    }
}