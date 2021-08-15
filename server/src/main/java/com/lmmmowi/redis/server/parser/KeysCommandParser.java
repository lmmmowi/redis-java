package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.KeysCommand;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.RedisCommandLine;
import com.lmmmowi.redis.server.exception.CommandParseException;
import com.lmmmowi.redis.server.exception.WrongNumberOfArgumentsException;

class KeysCommandParser implements RedisCommandParser {

    @Override
    public String getCommandKey() {
        return "keys";
    }

    @Override
    public RedisCommand parse(RedisCommandLine redisCommandLine) throws CommandParseException {
        String[] parts = redisCommandLine.getParts();
        if (parts.length != 2) {
            throw new WrongNumberOfArgumentsException(getCommandKey());
        }

        String pattern = parts[1];
        return new KeysCommand(pattern);
    }
}
