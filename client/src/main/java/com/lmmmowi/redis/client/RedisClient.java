package com.lmmmowi.redis.client;

public interface RedisClient {

    void startup();

    void shutdown();

    void send(String command);

    void setClientProcessor(ClientProcessor clientProcessor);
}
