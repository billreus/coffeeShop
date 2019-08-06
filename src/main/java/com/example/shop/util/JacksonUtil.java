package com.example.shop.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

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

    public static List<Integer> parseIntegerList(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);

            if (leaf != null){
                return mapper.convertValue(leaf, new TypeReference<List<Integer>>() {
                });}
        } catch (IOException e) {

        }
        return null;
    }

    public static Integer parseInteger(String body, String field) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(body);
            JsonNode leaf = node.get(field);
            if (leaf != null){
                return leaf.asInt();}
        } catch (IOException e) {

        }
        return null;
    }
}
