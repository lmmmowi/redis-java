package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.store.string.StringStorage;
import com.lmmmowi.redis.protocol.command.GetCommand;
import com.lmmmowi.redis.protocol.reply.FullBulkStringReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

class GetCommandExecutor extends AbstractCommandExecutor<GetCommand, StringStorage> {

    @Override
    protected RedisReply doExecute(GetCommand command, StringStorage storage) {
        String key = command.getKey();
        String value = storage.get(key);
        return new FullBulkStringReply(value);
    }
}
