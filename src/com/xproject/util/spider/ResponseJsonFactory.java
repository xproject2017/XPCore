package com.xproject.util.spider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("responseJson")
public class ResponseJsonFactory {

    final public static int REQUIRE_USER_ID_CODE = 497;
    final public static String REQUIRE_USER_ID_MSG = "require-user-id";
    final public static int ACCESS_DENIED_CODE = 498;
    final public static String ACCESS_DENIED_MSG = "access-denied";
    final public static int TOKEN_OVERDUE_CODE = 499;
    final public static String TOKEN_OVERDUE_MSG = "token-overdue";

    //@Resource
    private ObjectMapper jsonMapper = new ObjectMapper();

    public ObjectNode createOk() {
        return create(200, "ok");
    }

    public ObjectNode createOk(ObjectNode dataJson) {
        return create(200, "ok", dataJson);
    }

    public ObjectNode createOk(int okCode, String msg) {
        return create(200 + okCode, msg);
    }

    public ObjectNode createOk(int okCode, String msg, ObjectNode dataJson) {
        return create(200 + okCode, msg, dataJson);
    }

    public ObjectNode createBad() {
        return create(400, "bad");
    }

    public ObjectNode createBad(ObjectNode dataJson) {
        return create(400, "bad", dataJson);
    }

    public ObjectNode createBad(int badCode, String msg) {
        return create(400 + badCode, msg);
    }

    public ObjectNode createBad(int badCode, String msg, ObjectNode dataJson) {
        return create(400 + badCode, msg, dataJson);
    }

    public ObjectNode createError() {
        return create(500, "error");
    }

    public ObjectNode createError(ObjectNode dataJson) {
        return create(500, "error", dataJson);
    }

    public ObjectNode createError(int errorCode, String msg) {
        return create(500 + errorCode, msg);
    }

    public ObjectNode createError(int errorCode, String msg, ObjectNode dataJson) {
        return create(500 + errorCode, msg, dataJson);
    }

    public ObjectNode create(int code, String msg) {
        return create(code, msg, null);
    }

    public ObjectNode create(int code, String msg, ObjectNode dataJson) {
        ObjectNode responseJson = jsonMapper.createObjectNode();
        responseJson.put("code", code);
        responseJson.put("msg", msg);
        if (dataJson != null) responseJson.put("data", dataJson);
        return responseJson;
    }

    public JsonNode createSuccess() {
        ObjectNode responseJson = jsonMapper.createObjectNode();
        responseJson.put("code", 200);
        responseJson .put("msg", "ok");
        return responseJson;
    }

    public JsonNode createSuccess(Object o) {
        ObjectNode responseJson = jsonMapper.createObjectNode();
        responseJson.put("code", 200);
        responseJson .put("msg", "ok");
        responseJson.put("data", jsonMapper.convertValue(o, JsonNode.class));
        return responseJson;
    }

    public JsonNode createList(List list) {
        ObjectNode responseDataJson = jsonMapper.createObjectNode();
        ArrayNode arrayNode = jsonMapper.valueToTree(list);
        responseDataJson.putArray("result").addAll(arrayNode);
        ObjectNode responseJson = jsonMapper.createObjectNode();
        responseJson.put("code", 200);
        responseJson .put("msg", "ok");
        if (responseDataJson != null) {
            responseJson.put("data", responseDataJson);
        }
        return responseJson;
    }

    public JsonNode createCode(String str) {
        return create(Integer.parseInt(str.split(",")[0].toString()), str.split(",")[1]);
    }
}
