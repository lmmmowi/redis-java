package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.command.SelectCommand;
import com.lmmmowi.redis.server.commandline.RedisCommandLine;
import com.lmmmowi.redis.server.exception.CommandParseException;
import com.lmmmowi.redis.server.exception.WrongNumberOfArgumentsException;

class SelectCommandParser implements RedisCommandParser {

    @Override
    public String getCommandKey() {
        return "SELECT";
    }

    @Override
    public RedisCommand parse(RedisCommandLine redisCommandLine) throws CommandParseException {
        String[] parts = redisCommandLine.getParts();
        if (parts.length != 2 || parts[1] == null) {
            throw new WrongNumberOfArgumentsException(getCommandKey());
        }

        int dbIndex;
        try {
            dbIndex = Integer.parseInt(parts[1]);
        } catch (Exception e) {
            dbIndex = -1;
        }
        return new SelectCommand(dbIndex);
    }
}
