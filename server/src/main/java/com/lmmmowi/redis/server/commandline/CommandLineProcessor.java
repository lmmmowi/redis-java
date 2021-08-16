package com.lmmmowi.redis.server.commandline;

import com.lmmmowi.redis.protocol.reply.RedisReply;

public interface CommandLineProcessor {

    RedisReply process(RedisCommandLine redisCommandLine);
}
