package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.protocol.command.PingCommand;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.command.ScanCommand;

public class RedisCommandExecutors {

    private static final RedisCommandExecutors INSTANCE = new RedisCommandExecutors();

    private RedisCommandExecutors() {
    }

    public static RedisCommandExecutors getInstance() {
        return INSTANCE;
    }

    public RedisCommandExecutor getExecutor(RedisCommand command) {
        if (command instanceof PingCommand) {
            return new PingCommandExecutor();
        } else if (command instanceof ScanCommand) {
            return new ScanCommandExecutor();
        } else {
            return null;
        }
    }
}
