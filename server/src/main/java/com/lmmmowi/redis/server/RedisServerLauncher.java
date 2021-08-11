package com.lmmmowi.redis.server;

import com.lmmmowi.redis.server.netty.NettyRedisServer;
import com.lmmmowi.redis.server.netty.ServerConfiguration;

public class RedisServerLauncher {

    public static void main(String[] args) {
        new RedisServerLauncher().run();
    }

    public void run() {
        ServerConfiguration configuration = new ServerConfiguration();
        configuration.setPort(6378);

        NettyRedisServer server = new NettyRedisServer();
        server.setConfiguration(configuration);
        server.setServerProcessor(new DefaultServerProcessor());
        server.startup();
    }
}
