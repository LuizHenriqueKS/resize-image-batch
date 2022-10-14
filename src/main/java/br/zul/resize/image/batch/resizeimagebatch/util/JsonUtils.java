package br.zul.resize.image.batch.resizeimagebatch.util;

import java.io.File;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.zul.resize.image.batch.resizeimagebatch.exception.InterruptedExecutationException;

public class JsonUtils {
    
    private static ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT); 

    public static String stringify(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception ex) {
            throw new InterruptedExecutationException(ex);
        }
    }

    public static <T> T parse(String json, Class<T> targetClass) {
        try {
            return mapper.readValue(json, targetClass);
        } catch (JsonProcessingException ex) {
            throw new InterruptedExecutationException(ex);
        }
    }

    public static <T> T readFile(File file, Class<T> targetClass) {
        try {
            return mapper.readValue(file, targetClass);
        } catch (Exception ex){
            throw new InterruptedExecutationException(ex);
        }
    }

    public static void writeFile(File file, Object value) {
        try {
            mapper.writeValue(file, value);
        } catch (Exception ex) {
            throw new InterruptedExecutationException(ex);
        }
    }

}
