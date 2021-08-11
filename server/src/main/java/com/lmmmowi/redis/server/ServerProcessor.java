package com.lmmmowi.redis.server;

public interface ServerProcessor {

    void process(RedisCommandLine redisCommandLine);
}
