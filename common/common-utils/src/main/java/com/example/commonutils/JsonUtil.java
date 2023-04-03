package com.example.commonutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * com.example.commonutils
 *
 * @author xiaozhiwei
 * 2023/3/13
 * 20:22
 */
public class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJsonString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("写入json失败!", e);
        }
    }

    public static <T> T parse(String jsonString, Class<T> targetClass) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("读取json失败,json:" + jsonString + "\n", e);
        }
    }

    public static <T> T parse(String jsonString, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("读取json失败,json:" + jsonString + "\n", e);
        }
    }
}
