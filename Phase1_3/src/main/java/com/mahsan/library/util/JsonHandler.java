package com.mahsan.library.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private File file;
    private JsonNode rootJsonNode;
    private boolean isJsonFileValid;

    public JsonHandler(String fileAbsolutePath){
        file = new File(fileAbsolutePath);
        if(!file.exists()) createJsonFile();
        rootJsonNode = readJsonFile();
        isJsonFileValid = rootJsonNode != null;
    }

    public boolean isJsonFileValid(){
        return isJsonFileValid;
    }

    private void createJsonFile(){
        try {
            file.createNewFile();
        } catch (IOException _){}
    }

    private JsonNode readJsonFile(){
        try {
            return objectMapper.readTree(file);
        }catch (IOException exception){
            return null;
        }
    }

    public String getProperty(String key){
        if(!isJsonFileValid) return "";
        return rootJsonNode.get(key).asText();
    }

    public static String getProperty(JsonNode jsonNode , String key){
        return jsonNode.get(key).asText();
    }

    public void setProperty(String key , String value){
        if(!isJsonFileValid) return;
        JsonNode valueNode = objectMapper.valueToTree(value);
        if (rootJsonNode instanceof ObjectNode objectNode) {
            objectNode.set(key, valueNode);
        }
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file , rootJsonNode);
        }catch (IOException exception){
            System.out.println("property can't be set!");
        }
    }

    public static void setProperty(JsonNode jsonNode , File file , String key , String value){
        JsonNode valueNode = objectMapper.valueToTree(value);
        if(jsonNode instanceof ObjectNode objectNode){
            objectNode.set(key , valueNode);
        }
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file , jsonNode);
        }catch (IOException exception){
            System.out.println("property can't be set!");
        }
    }

    public ArrayList<JsonNode> getArrayElements(){
        if(!isJsonFileValid) return new ArrayList<>();
        if(!rootJsonNode.isArray()) return new ArrayList<>();
        ArrayList<JsonNode> elements = new ArrayList<>();
        for(JsonNode jsonNode : rootJsonNode){
            elements.add(jsonNode);
        }
        return elements;
    }
}
