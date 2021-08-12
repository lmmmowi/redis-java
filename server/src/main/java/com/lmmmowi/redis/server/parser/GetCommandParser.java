package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.GetCommand;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.RedisCommandLine;
import com.lmmmowi.redis.server.exception.CommandParseException;
import com.lmmmowi.redis.server.exception.WrongNumberOfArgumentsException;

class GetCommandParser implements RedisCommandParser {

    @Override
    public String getCommandKey() {
        return "get";
    }

    @Override
    public RedisCommand parse(RedisCommandLine redisCommandLine) throws CommandParseException {
        String[] parts = redisCommandLine.getParts();
        if (parts.length != 2 || parts[1] == null) {
            throw new WrongNumberOfArgumentsException(getCommandKey());
        }

        String key = parts[1].toLowerCase();
        return new GetCommand(key);
    }
}
