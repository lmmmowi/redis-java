package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.DbInstance;
import com.lmmmowi.redis.db.store.string.StringStorage;
import com.lmmmowi.redis.protocol.command.SetCommand;
import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.protocol.reply.StatusReply;

class SetCommandExecutor extends AbstractCommandExecutor<SetCommand> {

    @Override
    protected RedisReply doExecute(SetCommand command) {
        String key = command.getKey();
        String value = command.getValue();

        DbInstance db = getDbInstance();
        StringStorage storage = db.getStringStorage();
        storage.set(key, value);

        return new StatusReply("OK");
    }
}
