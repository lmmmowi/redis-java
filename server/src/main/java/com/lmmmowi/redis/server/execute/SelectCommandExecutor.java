package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.RedisDb;
import com.lmmmowi.redis.db.store.DataStorage;
import com.lmmmowi.redis.protocol.command.SelectCommand;
import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.protocol.reply.StatusReply;
import com.lmmmowi.redis.server.client.ClientInfo;

class SelectCommandExecutor extends AbstractCommandExecutor<SelectCommand, DataStorage> {

    @Override
    protected RedisReply doExecute(SelectCommand command) {
        int dbIndex = command.getDbIndex();
        RedisDb redisDb = ExecutorUtils.getRedisDb();
        redisDb.select(dbIndex);

        ClientInfo clientInfo = ExecutorUtils.currentClient();
        clientInfo.setDbIndex(dbIndex);

        return new StatusReply("OK");
    }
}
