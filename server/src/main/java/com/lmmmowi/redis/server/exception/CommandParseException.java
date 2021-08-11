package com.lmmmowi.redis.server.exception;

public class CommandParseException extends RuntimeException {

    public CommandParseException(String message) {
        super(message);
    }
}
