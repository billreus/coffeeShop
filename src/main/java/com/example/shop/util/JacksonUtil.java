package com.example.shop.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonUtil {

    public static String parseString(String body, String field){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try{
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if(leaf != null) {
                return leaf.asText();
            }
        }catch(IOException e){

        }
        return null;
    }
}
