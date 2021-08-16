package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.command.ScanCommand;
import com.lmmmowi.redis.server.commandline.RedisCommandLine;

class ScanCommandParser implements RedisCommandParser {

    @Override
    public String getCommandKey() {
        return "SCAN";
    }

    @Override
    public RedisCommand parse(RedisCommandLine redisCommandLine) {
        return new ScanCommand();
    }
}
