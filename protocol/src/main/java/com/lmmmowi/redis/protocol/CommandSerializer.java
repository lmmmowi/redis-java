package com.lmmmowi.redis.protocol;

public class CommandSerializer {

    private CommandSerializer() {
    }

    public static String serialize(String[] commandParts) {
        if (commandParts == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("*").append(commandParts.length).append("\r\n");
        for (String part : commandParts) {
            sb.append("$").append(part.length()).append("\r\n");
            sb.append(part).append("\r\n");
        }
        return sb.toString();
    }
}
