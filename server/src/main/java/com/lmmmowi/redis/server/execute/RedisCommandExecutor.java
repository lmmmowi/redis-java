package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.reply.RedisReply;

public interface RedisCommandExecutor {

    Class<? extends RedisCommand> getSupportCommandType();

    RedisReply execute(RedisCommand command);
}
