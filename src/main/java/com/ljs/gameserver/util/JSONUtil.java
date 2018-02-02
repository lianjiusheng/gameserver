package com.ljs.gameserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


/**
 * JSON工具类
 * **/
public class JSONUtil {



    public static String getJSONString(Object object) throws JsonProcessingException {
        if(object==null){
            return "";
        }
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public static  <T> T  readObject(Class<T> objectClass,String json)  throws IOException {
        if(json==null||json.isEmpty()){
            return null;
        }
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json,objectClass);
    }
}
