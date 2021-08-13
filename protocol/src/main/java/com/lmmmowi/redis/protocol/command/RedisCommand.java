package com.lmmmowi.redis.protocol.command;

public interface RedisCommand {

    void setRawParts(String[] parts);

    String serialize();
}
