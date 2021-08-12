package com.lmmmowi.redis.server;

import com.lmmmowi.redis.protocol.reply.RedisReply;

public interface ServerProcessor {

    RedisReply process(RedisCommandLine redisCommandLine);
}
