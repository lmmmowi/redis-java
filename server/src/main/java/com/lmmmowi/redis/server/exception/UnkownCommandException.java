package com.lmmmowi.redis.server.exception;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UnkownCommandException extends CommandParseException {

    public UnkownCommandException(String[] parts) {
        super(formatError(parts));
    }

    private static String formatError(String[] parts) {
        String command = parts[0];
        String args = IntStream.range(1, parts.length)
                .boxed()
                .map(i -> String.format("`%s`,", parts[i]))
                .collect(Collectors.joining(" "));
        return String.format("unknown command `%s`,with args beginning with: %s", command, args);
    }
}
