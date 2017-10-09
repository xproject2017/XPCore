package com.xproject.util.spider;

import org.springframework.http.HttpStatus;

/**
 * Created by Arei on 16/1/16.
 */
public class HttpStatusException extends Exception{
    private HttpStatus httpStatus;

    public HttpStatusException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
