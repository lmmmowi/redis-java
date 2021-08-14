package com.lmmmowi.redis.server;

import com.lmmmowi.redis.db.RedisDb;
import com.lmmmowi.redis.server.netty.NettyRedisServer;
import com.lmmmowi.redis.server.netty.ServerConfiguration;

public class RedisServerLauncher {

    public static void main(String[] args) {
        new RedisServerLauncher().run();
    }

    public void run() {
        RedisDb.getInstance().init();

        ServerConfiguration configuration = new ServerConfiguration();
        configuration.setPort(6378);

        NettyRedisServer server = new NettyRedisServer();
        server.setServerProcessor(new DefaultServerProcessor());
        server.setConfiguration(configuration);
        server.startup();

        Runtime.getRuntime().addShutdownHook(new Thread("redis-server-shutdown-hook") {
            @Override
            public void run() {
                RedisDb.getInstance().destroy();
            }
        });
    }
}
