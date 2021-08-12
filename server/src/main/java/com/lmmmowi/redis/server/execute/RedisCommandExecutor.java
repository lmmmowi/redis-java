package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.reply.RedisReply;

public interface RedisCommandExecutor<T extends RedisCommand> {

    Class<T> getSupportCommandType();

    RedisReply execute(T command);
}
