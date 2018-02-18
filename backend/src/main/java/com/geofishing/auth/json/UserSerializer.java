package com.geofishing.auth.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.geofishing.auth.social.FacebookUser;

import java.io.IOException;

public class UserSerializer extends JsonSerializer<FacebookUser> {
    @Override
    public void serialize(FacebookUser value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("lastname", value.getLastName());
        gen.writeStringField("id", value.getId());
        gen.writeStringField("email", value.getEmail());
        gen.writeEndObject();

    }
}