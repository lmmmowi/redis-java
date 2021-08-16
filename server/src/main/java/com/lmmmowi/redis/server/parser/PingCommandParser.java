package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.PingCommand;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.commandline.RedisCommandLine;
import com.lmmmowi.redis.server.exception.CommandParseException;
import com.lmmmowi.redis.server.exception.WrongNumberOfArgumentsException;

class PingCommandParser implements RedisCommandParser {

    @Override
    public String getCommandKey() {
        return "PING";
    }

    @Override
    public RedisCommand parse(RedisCommandLine redisCommandLine) throws CommandParseException {
        String[] parts = redisCommandLine.getParts();
        if (parts.length > 1) {
            throw new WrongNumberOfArgumentsException(getCommandKey());
        }

        return new PingCommand();
    }
}
