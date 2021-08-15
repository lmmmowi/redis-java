package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.store.list.ListStorage;
import com.lmmmowi.redis.protocol.command.LpushxCommand;
import com.lmmmowi.redis.protocol.reply.IntegerReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

public class LpushxCommandExecutor extends AbstractCommandExecutor<LpushxCommand, ListStorage> {

    @Override
    protected RedisReply doExecute(LpushxCommand command, ListStorage storage) {
        String key = command.getKey();
        String[] values = command.getValues();

        long length = storage.lpushx(key, values);
        return new IntegerReply(length);
    }
}
