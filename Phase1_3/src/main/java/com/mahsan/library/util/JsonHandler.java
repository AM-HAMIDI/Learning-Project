package com.mahsan.library.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class JsonHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private File file;
    private JsonNode rootJsonNode;
    private boolean isJsonFileValid;

    public JsonHandler(String fileAbsolutePath){
        file = new File(fileAbsolutePath);
        if(!file.exists()){
            if(createJsonFile())
                System.out.println(file.getName() + " created!");
            else
                System.out.println(file.getName() + " couldn't created!");
        }
        rootJsonNode = readJsonFile();
        if(rootJsonNode == null){
            System.out.println(file.getName() + " is not valid json file!");
            isJsonFileValid = false;
        } else
            isJsonFileValid = true;
    }

    private boolean createJsonFile(){
        try {
            return file.createNewFile();
        } catch (IOException exception){
            System.out.println(exception);
            return false;
        }
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
}
