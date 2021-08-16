package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.command.SetCommand;
import com.lmmmowi.redis.server.commandline.RedisCommandLine;
import com.lmmmowi.redis.server.exception.CommandParseException;
import com.lmmmowi.redis.server.exception.WrongNumberOfArgumentsException;

class SetCommandParser implements RedisCommandParser {

    @Override
    public String getCommandKey() {
        return "SET";
    }

    @Override
    public RedisCommand parse(RedisCommandLine redisCommandLine) throws CommandParseException {
        String[] parts = redisCommandLine.getParts();
        if (parts.length != 3 || parts[1] == null || parts[2] == null) {
            throw new WrongNumberOfArgumentsException(getCommandKey());
        }

        String key = parts[1].toLowerCase();
        String value = parts[2].toLowerCase();
        return new SetCommand(key, value);
    }
}
