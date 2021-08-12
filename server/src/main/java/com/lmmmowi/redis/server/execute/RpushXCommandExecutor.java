package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.RedisDb;
import com.lmmmowi.redis.db.list.ListStorage;
import com.lmmmowi.redis.protocol.command.RpushXCommand;
import com.lmmmowi.redis.protocol.reply.IntegerReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

public class RpushXCommandExecutor extends AbstractCommandExecutor<RpushXCommand> {
    @Override
    protected RedisReply doExecute(RpushXCommand command) {
        String key = command.getKey();
        String[] values = command.getValues();

        RedisDb redisDb = RedisDb.getInstance();
        ListStorage listStorage = redisDb.getListStorage();
        long length = listStorage.rpushx(key, values);
        return new IntegerReply(length);
    }
}
