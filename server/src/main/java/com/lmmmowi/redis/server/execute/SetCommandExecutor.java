package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.store.string.StringStorage;
import com.lmmmowi.redis.protocol.command.SetCommand;
import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.protocol.reply.StatusReply;

class SetCommandExecutor extends AbstractCommandExecutor<SetCommand, StringStorage> {

    @Override
    protected RedisReply doExecute(SetCommand command, StringStorage storage) {
        String key = command.getKey();
        String value = command.getValue();

        storage.set(key, value);
        return new StatusReply("OK");
    }
}
