package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.DbInstance;
import com.lmmmowi.redis.db.store.DataStorage;
import com.lmmmowi.redis.protocol.command.KeysCommand;
import com.lmmmowi.redis.protocol.reply.ArrayReply;
import com.lmmmowi.redis.protocol.reply.FullBulkStringReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class KeysCommandExecutor extends AbstractCommandExecutor<KeysCommand, DataStorage> {

    @Override
    protected RedisReply doExecute(KeysCommand command) {
        String pattern = command.getPattern();
        DbInstance db = ExecutorUtils.currentDbInstance();
        Collection<String> keys = db.keys(pattern);

        List<RedisReply> keyReplies = keys.stream().map(FullBulkStringReply::new).collect(Collectors.toList());
        return new ArrayReply(keyReplies);
    }
}
