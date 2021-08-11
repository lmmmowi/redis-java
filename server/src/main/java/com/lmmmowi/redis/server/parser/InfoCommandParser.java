package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.InfoCommand;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.exception.WrongNumberOfArgumentsException;
import com.lmmmowi.redis.server.RedisCommandLine;

public class InfoCommandParser implements RedisCommandParser {

    @Override
    public String getCommandKey() {
        return "info";
    }

    @Override
    public RedisCommand parse(RedisCommandLine redisCommandLine) {
        String[] parts = redisCommandLine.getParts();
        if (parts.length > 2) {
            throw new WrongNumberOfArgumentsException(getCommandKey());
        }

        String section = parts.length == 2 ? parts[1] : null;
        return new InfoCommand(section);
    }
}
