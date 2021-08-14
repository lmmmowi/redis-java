package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.DbInstance;
import com.lmmmowi.redis.db.store.list.ListStorage;
import com.lmmmowi.redis.protocol.command.RpushxCommand;
import com.lmmmowi.redis.protocol.reply.IntegerReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

public class RpushxCommandExecutor extends AbstractCommandExecutor<RpushxCommand> {
    @Override
    protected RedisReply doExecute(RpushxCommand command) {
        String key = command.getKey();
        String[] values = command.getValues();

        DbInstance db = getDbInstance();
        ListStorage listStorage = db.getListStorage();
        long length = listStorage.rpushx(key, values);
        return new IntegerReply(length);
    }
}
