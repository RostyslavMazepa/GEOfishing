package com.geofishing.controllers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.geofishing.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserListSerializer extends JsonSerializer<Set<User>>
{

    public class ListUser{
        private int userid;
        private String username;
        public int getUserid() {return userid;}
        public void setUserid(int id) {this.userid = id;}
        public String getUsername() {return username;}
        public void setUsername(String username) {this.username = username;}
    }   ;

    @Override
    public void serialize(Set<User> users, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartArray();
        Map<Integer,String> ids = new HashMap<>();
            for (User user : users) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("userId", user.getUserId());
                jsonGenerator.writeStringField("username",user.getUsername());
                jsonGenerator.writeEndObject();
            }
        jsonGenerator.writeEndArray();
            //jsonGenerator.writeObject(ids);
    }
}
