package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.DbInstance;
import com.lmmmowi.redis.db.store.string.StringStorage;
import com.lmmmowi.redis.protocol.command.GetCommand;
import com.lmmmowi.redis.protocol.reply.FullBulkStringReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

class GetCommandExecutor extends AbstractCommandExecutor<GetCommand> {

    @Override
    protected RedisReply doExecute(GetCommand command) {
        String key = command.getKey();

        DbInstance db = getDbInstance();
        StringStorage storage = db.getStringStorage();
        String value = storage.get(key);
        return new FullBulkStringReply(value);
    }
}
