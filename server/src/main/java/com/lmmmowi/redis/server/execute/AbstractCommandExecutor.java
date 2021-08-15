package com.lmmmowi.redis.server.execute;

import cn.hutool.core.util.ClassUtil;
import com.lmmmowi.redis.db.DbInstance;
import com.lmmmowi.redis.db.RedisDb;
import com.lmmmowi.redis.db.persist.aof.AofManager;
import com.lmmmowi.redis.db.store.DataStorage;
import com.lmmmowi.redis.db.store.list.ListStorage;
import com.lmmmowi.redis.db.store.string.StringStorage;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.server.ClientHolder;
import com.lmmmowi.redis.server.ClientInfo;

abstract class AbstractCommandExecutor<T extends RedisCommand, S extends DataStorage> implements RedisCommandExecutor {

    private final ClientHolder clientHolder = ClientHolder.getInstance();
    private final AofManager aofManager = AofManager.getInstance();

    private Class<T> redisCommandType;
    private Class<S> dataStorageType;

    AbstractCommandExecutor() {
        this.redisCommandType = (Class<T>) ClassUtil.getTypeArgument(getClass(), 0);
        this.dataStorageType = (Class<S>) ClassUtil.getTypeArgument(getClass(), 1);
    }

    @Override
    public Class<T> getSupportCommandType() {
        return this.redisCommandType;
    }

    @Override
    public RedisReply execute(RedisCommand command) {
        RedisReply reply;

        S storage = this.getStorage();
        if (storage != null) {
            reply = this.doExecute((T) command, storage);
        } else {
            reply = this.doExecute((T) command);
        }

        // 命令执行完成后追加AOF日志
        aofManager.append(currentDbIndex(), command);

        return reply;
    }

    protected RedisReply doExecute(T command, S storage) {
        throw new UnsupportedOperationException();
    }

    protected RedisReply doExecute(T command) {
        throw new UnsupportedOperationException();
    }

    protected S getStorage() {
        DataStorage dataStorage = null;
        if (StringStorage.class.equals(dataStorageType)) {
            dataStorage = currentDbInstance().getStringStorage();
        } else if (ListStorage.class.equals(dataStorageType)) {
            dataStorage = currentDbInstance().getListStorage();
        }
        return (S) dataStorage;
    }

    protected DbInstance currentDbInstance() {
        int dbIndex = currentDbIndex();
        return RedisDb.getInstance().select(dbIndex);
    }

    private int currentDbIndex() {
        ClientInfo clientInfo = clientHolder.getClientInfo();
        return clientInfo.getDbIndex();
    }
}
