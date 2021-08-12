package com.lmmmowi.redis.server.execute;

import cn.hutool.core.util.ClassUtil;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.reply.RedisReply;

abstract class AbstractCommandExecutor<T extends RedisCommand> implements RedisCommandExecutor {

    @Override
    public Class<T> getSupportCommandType() {
        return (Class<T>) ClassUtil.getTypeArgument(getClass(), 0);
    }

    @Override
    public RedisReply execute(RedisCommand command) {
        return this.doExecute((T) command);
    }

    protected abstract RedisReply doExecute(T command);
}
