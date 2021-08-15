package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.store.list.ListStorage;
import com.lmmmowi.redis.protocol.command.RpushxCommand;
import com.lmmmowi.redis.protocol.reply.IntegerReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

public class RpushxCommandExecutor extends AbstractCommandExecutor<RpushxCommand, ListStorage> {

    @Override
    protected RedisReply doExecute(RpushxCommand command, ListStorage storage) {
        String key = command.getKey();
        String[] values = command.getValues();

        long length = storage.rpushx(key, values);
        return new IntegerReply(length);
    }
}
