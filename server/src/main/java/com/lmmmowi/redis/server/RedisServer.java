package com.lmmmowi.redis.server;

import com.lmmmowi.redis.configuration.ServerConfiguration;

public interface RedisServer {

    void setConfiguration(ServerConfiguration configuration);

    void startup();

    void shutdown();
}
