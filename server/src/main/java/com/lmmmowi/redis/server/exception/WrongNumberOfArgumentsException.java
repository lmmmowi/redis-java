package com.lmmmowi.redis.server.exception;

public class WrongNumberOfArgumentsException extends CommandParseException {

    public WrongNumberOfArgumentsException(String command) {
        super(formatError(command));
    }

    private static String formatError(String command) {
        return String.format("wrong number of arguments for '%s' command", command);
    }
}
