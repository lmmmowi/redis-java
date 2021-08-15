package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.store.list.ListStorage;
import com.lmmmowi.redis.protocol.command.LpushCommand;
import com.lmmmowi.redis.protocol.reply.IntegerReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

class LpushCommandExecutor extends AbstractCommandExecutor<LpushCommand, ListStorage> {

    @Override
    protected RedisReply doExecute(LpushCommand command, ListStorage storage) {
        String key = command.getKey();
        String[] values = command.getValues();

        long length = storage.lpush(key, values);
        return new IntegerReply(length);
    }
}
