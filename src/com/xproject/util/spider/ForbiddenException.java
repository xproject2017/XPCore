package com.xproject.util.spider;

/**
 * Created by Arei on 16/1/4.
 */
public class ForbiddenException extends Exception{
    private final static String msg = "forbidden";
    public ForbiddenException() {
        super(msg);
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
