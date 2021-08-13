package com.lmmmowi.redis.protocol.command;

abstract class AbstractCommand implements RedisCommand {

    private String[] parts;

    @Override
    public void setRawParts(String[] parts) {
        this.parts = parts;
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("*").append(parts.length).append("\r\n");
        for (String part : parts) {
            sb.append("$").append(part.length()).append("\r\n");
            sb.append(part).append("\r\n");
        }
        return sb.toString();
    }
}
