package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.commandline.RedisCommandLine;
import com.lmmmowi.redis.server.exception.CommandParseException;

public interface RedisCommandParser {

    String getCommandKey();

    RedisCommand parse(RedisCommandLine redisCommandLine) throws CommandParseException;
}
