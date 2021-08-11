package com.lmmmowi.redis.server;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.parser.RedisCommandParseKit;

public class DefaultServerProcessor implements ServerProcessor {

    private final RedisCommandParseKit redisCommandParseKit = RedisCommandParseKit.getInstance();

    @Override
    public void process(RedisCommandLine redisCommandLine) {
        RedisCommand redisCommand = redisCommandParseKit.parse(redisCommandLine);
        System.out.println(redisCommand);
    }
}
