package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.RedisDb;
import com.lmmmowi.redis.db.aof.AofManager;
import com.lmmmowi.redis.db.store.DataStorage;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.server.client.ClientInfo;
import com.lmmmowi.redis.util.ClassUtils;

abstract class AbstractCommandExecutor<T extends RedisCommand, S extends DataStorage> implements RedisCommandExecutor {

    private Class<T> commandType;
    private Class<S> dataStorageType;

    AbstractCommandExecutor() {
        this.commandType = (Class<T>) ClassUtils.getTypeArgument(getClass(), 0);
        this.dataStorageType = (Class<S>) ClassUtils.getTypeArgument(getClass(), 1);
    }

    @Override
    public Class<T> getSupportCommandType() {
        return this.commandType;
    }

    @Override
    public RedisReply execute(RedisCommand command) {
        DataStorage storage = ExecutorUtils.getStorage(dataStorageType);
        T typedCommand = commandType.cast(command);
        S typedStorage = dataStorageType.cast(storage);

        RedisReply reply;
        if (storage != null) {
            reply = this.doExecute(typedCommand, typedStorage);
        } else {
            reply = this.doExecute(typedCommand);
        }

        // 命令执行完成后追加AOF日志
        this.appendLog(command);

        return reply;
    }

    protected RedisReply doExecute(T command, S storage) {
        throw new UnsupportedOperationException();
    }

    protected RedisReply doExecute(T command) {
        throw new UnsupportedOperationException();
    }

    private void appendLog(RedisCommand command) {
        ClientInfo clientInfo = ExecutorUtils.currentClient();
        if (clientInfo.isSystemClient()) {
            return;
        }

        RedisDb redisDb = ExecutorUtils.getRedisDb();
        AofManager aofManager = redisDb.getAofManager();
        int dbIndex = clientInfo.getDbIndex();
        aofManager.append(dbIndex, command);
    }
}
