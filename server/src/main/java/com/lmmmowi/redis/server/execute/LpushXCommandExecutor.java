package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.RedisDb;
import com.lmmmowi.redis.db.list.ListStorage;
import com.lmmmowi.redis.protocol.command.LpushXCommand;
import com.lmmmowi.redis.protocol.reply.IntegerReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

public class LpushXCommandExecutor extends AbstractCommandExecutor<LpushXCommand> {

    @Override
    protected RedisReply doExecute(LpushXCommand command) {
        String key = command.getKey();
        String[] values = command.getValues();

        RedisDb redisDb = RedisDb.getInstance();
        ListStorage listStorage = redisDb.getListStorage();
        long length = listStorage.lpushx(key, values);

        return new IntegerReply(length);
    }
}
