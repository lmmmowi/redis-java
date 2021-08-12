package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.LpushXCommand;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.RedisCommandLine;
import com.lmmmowi.redis.server.exception.CommandParseException;
import com.lmmmowi.redis.server.exception.WrongNumberOfArgumentsException;

import java.util.Arrays;

/**
 * @Author: 11119824
 * @Date: 2021/8/12
 * @Description:
 */
public class LpushXCommandParser implements RedisCommandParser {
    @Override
    public String getCommandKey() {
        return "lpushx";
    }

    @Override
    public RedisCommand parse(RedisCommandLine redisCommandLine) throws CommandParseException {
        String[] parts = redisCommandLine.getParts();
        if (parts.length <= 2) {
            throw new WrongNumberOfArgumentsException(getCommandKey());
        }

        String key = parts[1].toLowerCase();
        String[] values = Arrays.copyOfRange(parts, 2, parts.length);
        return new LpushXCommand(key, values);
    }
}
