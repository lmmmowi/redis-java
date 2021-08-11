package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.RedisCommandLine;

public interface RedisCommandParser {

    String getCommandKey();

    RedisCommand parse(RedisCommandLine redisCommandLine);
}
