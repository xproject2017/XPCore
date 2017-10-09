package com.xproject.util.spider;

import org.springframework.http.HttpStatus;

/**
 * Created by Arei on 16/3/16.
 */
public class ServerErrorException extends HttpStatusException{
    private final static String msg = "unknown server error";
    public ServerErrorException() {
        super(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ServerErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
