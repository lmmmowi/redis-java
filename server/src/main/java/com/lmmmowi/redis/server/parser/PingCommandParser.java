package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.PingCommand;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.exception.WrongNumberOfArgumentsException;
import com.lmmmowi.redis.server.RedisCommandLine;

public class PingCommandParser implements RedisCommandParser {

    @Override
    public String getCommandKey() {
        return "ping";
    }

    @Override
    public RedisCommand parse(RedisCommandLine redisCommandLine) {
        String[] parts = redisCommandLine.getParts();
        if (parts.length > 1) {
            throw new WrongNumberOfArgumentsException(getCommandKey());
        }

        return new PingCommand();
    }
}
