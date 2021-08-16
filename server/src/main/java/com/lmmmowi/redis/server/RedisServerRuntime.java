package com.lmmmowi.redis.server;

import com.lmmmowi.redis.configuration.AofConfiguration;
import com.lmmmowi.redis.configuration.RedisConfiguration;
import com.lmmmowi.redis.configuration.ServerConfiguration;
import com.lmmmowi.redis.db.RedisDb;
import com.lmmmowi.redis.server.client.ClientHolder;
import com.lmmmowi.redis.server.commandline.CommandLineProcessor;
import com.lmmmowi.redis.server.commandline.DefaultCommandLineProcessor;
import com.lmmmowi.redis.server.netty.NettyRedisServer;
import lombok.Getter;

public class RedisServerRuntime {

    private static RedisServerRuntime instance;

    private RedisConfiguration configuration;

    @Getter
    private RedisDb redisDb;

    @Getter
    private ClientHolder clientHolder;

    @Getter
    private CommandLineProcessor commandLineProcessor;

    private RedisServer redisServer;

    RedisServerRuntime(RedisConfiguration configuration) {
        this.configuration = configuration;
        instance = this;
    }

    public static RedisServerRuntime get() {
        return instance;
    }

    void run() {
        // 初始化内存数据库
        initRedisDb(configuration.getAof());

        // 初始化命令处理器
        commandLineProcessor = DefaultCommandLineProcessor.create();

        // 初始化客户端连接信息存储
        clientHolder = ClientHolder.create();

        // 从磁盘恢复数据
        redisDb.resumeData();

        // 暴露对外服务
        export(configuration.getServer());

        // 注册关闭钩子，处理资源释放等事项
        registerShutdownHook();
    }

    private void initRedisDb(AofConfiguration aofConfiguration) {
        redisDb = RedisDb.create(aofConfiguration);
        redisDb.init();
    }

    private void export(ServerConfiguration serverConfiguration) {
        redisServer = new NettyRedisServer();
        redisServer.setConfiguration(serverConfiguration);
        redisServer.startup();
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread("redis-server-shutdown-hook") {
            @Override
            public void run() {
                // 关闭对外服务
                redisServer.shutdown();

                // 释放数据库
                redisDb.destroy();
            }
        });
    }
}
