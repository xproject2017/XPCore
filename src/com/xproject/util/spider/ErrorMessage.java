package com.xproject.util.spider;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Arei on 16/1/4.
 */
public class ErrorMessage {

    @JsonProperty("message")
    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
