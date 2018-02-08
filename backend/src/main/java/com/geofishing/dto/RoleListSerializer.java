package com.geofishing.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.geofishing.model.auth.Role;
import org.hibernate.collection.internal.PersistentSet;

import java.io.IOException;
import java.util.Iterator;

public class RoleListSerializer extends JsonSerializer<PersistentSet>
{
    @Override
    public void serialize(PersistentSet o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartArray();
        if (o.isEmpty()) {
            jsonGenerator.writeString("GUEST");
            jsonGenerator.writeEndArray();
            return ;
        }
        final Iterator iterator = o.iterator();
        while (iterator.hasNext()){
            jsonGenerator.writeString(((Role)iterator.next()).getName());
        }
        jsonGenerator.writeEndArray();
    }
}
