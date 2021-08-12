package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.InfoCommand;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.RedisCommandLine;
import com.lmmmowi.redis.server.exception.CommandParseException;
import com.lmmmowi.redis.server.exception.WrongNumberOfArgumentsException;

class InfoCommandParser implements RedisCommandParser {

    @Override
    public String getCommandKey() {
        return "info";
    }

    @Override
    public RedisCommand parse(RedisCommandLine redisCommandLine) throws CommandParseException {
        String[] parts = redisCommandLine.getParts();
        if (parts.length > 2) {
            throw new WrongNumberOfArgumentsException(getCommandKey());
        }

        String section = parts.length == 2 ? parts[1] : null;
        return new InfoCommand(section);
    }
}
