package com.xproject.util.spider;

import org.springframework.http.HttpStatus;

/**
 * Created by Arei on 16/1/16.
 */
public class BadRequestException extends HttpStatusException {
    private final static String msg = "bad request";
    public BadRequestException() {
        super(msg, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
