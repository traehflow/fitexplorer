package com.vsoft.fitexplorer.service.impl;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;
import java.util.List;


public class ActivityIdDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        System.out.println("Deserialization invoked for 'id' field"); // Debugging
        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
            Map<String, Object> links = mapper.readValue(parser, new TypeReference<>() {});
            return ((List<Map<String, String>>) links.get("self")).get(0).get("id");

    }
}
