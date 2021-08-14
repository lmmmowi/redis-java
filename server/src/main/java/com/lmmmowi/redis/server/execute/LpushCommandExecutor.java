package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.DbInstance;
import com.lmmmowi.redis.db.store.list.ListStorage;
import com.lmmmowi.redis.protocol.command.LpushCommand;
import com.lmmmowi.redis.protocol.reply.IntegerReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

class LpushCommandExecutor extends AbstractCommandExecutor<LpushCommand> {

    @Override
    protected RedisReply doExecute(LpushCommand command) {
        String key = command.getKey();
        String[] values = command.getValues();

        DbInstance db = getDbInstance();
        ListStorage storage = db.getListStorage();
        long length = storage.lpush(key, values);

        return new IntegerReply(length);
    }
}
