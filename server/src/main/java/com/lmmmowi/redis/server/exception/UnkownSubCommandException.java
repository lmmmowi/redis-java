package com.lmmmowi.redis.server.exception;

public class UnkownSubCommandException extends CommandParseException {

    public UnkownSubCommandException(String subCommand) {
        super(formatError(subCommand));
    }

    private static String formatError(String subCommand) {
        return String.format("Unknown subcommand or wrong number of arguments for '%s'.", subCommand);
    }
}
