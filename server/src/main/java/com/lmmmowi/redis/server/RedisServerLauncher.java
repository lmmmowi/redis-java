package com.lmmmowi.redis.server;

import com.lmmmowi.redis.configuration.AofConfiguration;
import com.lmmmowi.redis.configuration.RedisConfiguration;
import com.lmmmowi.redis.configuration.ServerConfiguration;

public class RedisServerLauncher {

    public static void main(String[] args) {
        RedisConfiguration configuration = parseConfiguration(args);
        new RedisServerRuntime(configuration).run();
    }

    private static RedisConfiguration parseConfiguration(String[] args) {
        ServerConfiguration serverConfiguration = new ServerConfiguration();
        serverConfiguration.setPort(6378);

        AofConfiguration aofConfiguration = new AofConfiguration();
        aofConfiguration.setEnabled(true);
        aofConfiguration.setAppendOnlyFilePath("data/appendonly.aof");

        RedisConfiguration redisConfiguration = new RedisConfiguration();
        redisConfiguration.setServer(serverConfiguration);
        redisConfiguration.setAof(aofConfiguration);
        return redisConfiguration;
    }
}
