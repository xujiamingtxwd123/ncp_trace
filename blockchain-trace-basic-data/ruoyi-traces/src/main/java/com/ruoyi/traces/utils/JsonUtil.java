package com.ruoyi.traces.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


public class JsonUtil {
    public static String serialize(Object object){
        // JSON对象序列化
        String jsonString = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createJsonGenerator(stringWriter);
            objectMapper.writeValue(jsonGenerator, object);
            jsonGenerator.close();
            jsonString = stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static <T> T deserialize(String employeeJson, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(employeeJson, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  static <T> List<T> getObjectsFromJson(String in, Class<T> clsT) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonParser parser = objectMapper.getJsonFactory().createJsonParser(in);
        JsonNode nodes = parser.readValueAsTree();
        List<T> list  = new ArrayList<T>(nodes.size());
        for(JsonNode node : nodes){
            list.add(objectMapper.readValue(node.asText(), clsT));
        }
        return list;
    }
}