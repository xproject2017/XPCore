package com.xproject.util.spider;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Hao on 2015/4/1.
 */
public class YCJsonUtils {
    public static String getText(JsonNode jsonNode, String fieldName, String emptyNullDefaultValue) {
        if (jsonNode == null) return emptyNullDefaultValue;
        return jsonNode.has(fieldName) ? jsonNode.get(fieldName).asText() : emptyNullDefaultValue;
    }

    public static Boolean getBoolean(JsonNode jsonNode, String fieldName, Boolean emptyNullDefaultValue) {
        if (jsonNode == null) return emptyNullDefaultValue;
        return jsonNode.has(fieldName) ? Boolean.valueOf(jsonNode.get(fieldName).asBoolean()) : emptyNullDefaultValue;
    }

    public static Integer getInt(JsonNode jsonNode, String fieldName, Integer emptyNullDefaultValue) {
        if (jsonNode == null) return emptyNullDefaultValue;
        return jsonNode.has(fieldName) ? Integer.valueOf(jsonNode.get(fieldName).asInt()) : emptyNullDefaultValue;
    }

    public static Long getLong(JsonNode jsonNode, String fieldName, Long emptyNullDefaultValue) {
        if (jsonNode == null) return emptyNullDefaultValue;
        return jsonNode.has(fieldName) ? Long.valueOf(jsonNode.get(fieldName).asLong()) : emptyNullDefaultValue;
    }

    public static Double getDouble(JsonNode jsonNode, String fieldName, Double emptyNullDefaultValue) {
        if (jsonNode == null) return emptyNullDefaultValue;
        return jsonNode.has(fieldName) ? Double.valueOf(jsonNode.get(fieldName).asDouble()) : emptyNullDefaultValue;
    }

    public static Date getDate(JsonNode jsonNode, String fieldName, Date emptyNullDefaultValue) {
        if (jsonNode == null) return emptyNullDefaultValue;
        return jsonNode.has(fieldName) ? new Date(jsonNode.get(fieldName).asLong()) : emptyNullDefaultValue;
    }

    public static ArrayList getArrayList(JsonNode jsonNode, String fieldName, Object o) {
        return jsonNode.has(fieldName) ? new ObjectMapper().convertValue(jsonNode.get(fieldName), ArrayList.class) : (ArrayList) o;
    }

 /*   public static <T> List<T> getArrayList(String str, Class<T> a) {
        JSONArray jsonarray = JSONArray.fromObject(str);
        return (List) JSONArray.toCollection(jsonarray, a);
    }*/

    public static <T> T getArrayWithClass(JsonNode node, Class<T> a) throws IOException {
        return new ObjectMapper().readValue(node.toString(), a);
    }
}
