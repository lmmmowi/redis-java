package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.RedisDb;
import com.lmmmowi.redis.db.string.StringStorage;
import com.lmmmowi.redis.protocol.command.SetCommand;
import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.protocol.reply.StatusReply;

class SetCommandExecutor extends AbstractCommandExecutor<SetCommand> {

    @Override
    protected RedisReply doExecute(SetCommand command) {
        String key = command.getKey();
        String value = command.getValue();

        RedisDb redisDb = RedisDb.getInstance();
        StringStorage storage = redisDb.getStringStorage();
        storage.set(key, value);

        return new StatusReply("OK");
    }
}
