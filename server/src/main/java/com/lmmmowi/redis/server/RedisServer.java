package com.lmmmowi.redis.server;

public interface RedisServer {

    void startup();

    void shutdown();

    void setServerProcessor(ServerProcessor serverProcessor);
}
