package com.lmmmowi.redis.server.execute;

import cn.hutool.core.util.ClassUtil;
import com.lmmmowi.redis.persist.aof.AofManager;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.reply.RedisReply;

abstract class AbstractCommandExecutor<T extends RedisCommand> implements RedisCommandExecutor {

    @Override
    public Class<T> getSupportCommandType() {
        return (Class<T>) ClassUtil.getTypeArgument(getClass(), 0);
    }

    @Override
    public RedisReply execute(RedisCommand command) {
        RedisReply reply = this.doExecute((T) command);

        // 命令执行完成后追加AOF日志
        AofManager.getInstance().append(command);

        return reply;
    }

    protected abstract RedisReply doExecute(T command);
}
