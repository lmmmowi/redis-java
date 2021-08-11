package com.lmmmowi.redis.client;

import com.lmmmowi.redis.protocol.reply.RedisReply;

public interface ClientProcessor {

    void process(RedisReply redisReply);
}
