package com.lmmmowi.redis.protocol.command;

import com.lmmmowi.redis.protocol.CommandSerializer;

abstract class AbstractCommand implements RedisCommand {

    private String[] parts;

    @Override
    public void setRawParts(String[] parts) {
        this.parts = parts;
    }

    @Override
    public String serialize() {
        return CommandSerializer.serialize(parts);
    }
}
