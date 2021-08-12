package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.RedisDb;
import com.lmmmowi.redis.db.string.StringStorage;
import com.lmmmowi.redis.protocol.command.GetCommand;
import com.lmmmowi.redis.protocol.reply.FullBulkStringReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

class GetCommandExecutor extends AbstractCommandExecutor<GetCommand> {

    @Override
    protected RedisReply doExecute(GetCommand command) {
        String key = command.getKey();

        RedisDb redisDb = RedisDb.getInstance();
        StringStorage storage = redisDb.getStringStorage();
        String value = storage.get(key);
        return new FullBulkStringReply(value);
    }
}
