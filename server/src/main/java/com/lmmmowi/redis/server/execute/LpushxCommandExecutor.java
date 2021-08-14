package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.DbInstance;
import com.lmmmowi.redis.db.store.list.ListStorage;
import com.lmmmowi.redis.protocol.command.LpushxCommand;
import com.lmmmowi.redis.protocol.reply.IntegerReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

public class LpushxCommandExecutor extends AbstractCommandExecutor<LpushxCommand> {

    @Override
    protected RedisReply doExecute(LpushxCommand command) {
        String key = command.getKey();
        String[] values = command.getValues();

        DbInstance db = getDbInstance();
        ListStorage listStorage = db.getListStorage();
        long length = listStorage.lpushx(key, values);

        return new IntegerReply(length);
    }
}
