package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.command.ScanCommand;
import com.lmmmowi.redis.server.RedisCommandLine;

public class ScanCommandParser implements RedisCommandParser {

    @Override
    public String getCommandKey() {
        return "scan";
    }

    @Override
    public RedisCommand parse(RedisCommandLine redisCommandLine) {
        return new ScanCommand();
    }
}
