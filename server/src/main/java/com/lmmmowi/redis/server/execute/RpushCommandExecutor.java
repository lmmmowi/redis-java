package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.store.list.ListStorage;
import com.lmmmowi.redis.protocol.command.RpushCommand;
import com.lmmmowi.redis.protocol.reply.IntegerReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

class RpushCommandExecutor extends AbstractCommandExecutor<RpushCommand, ListStorage> {

    @Override
    protected RedisReply doExecute(RpushCommand command, ListStorage storage) {
        String key = command.getKey();
        String[] values = command.getValues();

        long length = storage.rpush(key, values);
        return new IntegerReply(length);
    }
}
