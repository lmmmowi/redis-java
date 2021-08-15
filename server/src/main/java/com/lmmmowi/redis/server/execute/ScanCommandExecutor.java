package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.DbInstance;
import com.lmmmowi.redis.db.store.DataStorage;
import com.lmmmowi.redis.protocol.command.ScanCommand;
import com.lmmmowi.redis.protocol.reply.ArrayReply;
import com.lmmmowi.redis.protocol.reply.FullBulkStringReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class ScanCommandExecutor extends AbstractCommandExecutor<ScanCommand, DataStorage> {

    @Override
    protected RedisReply doExecute(ScanCommand command) {
        DbInstance db = currentDbInstance();
        Collection<String> keys = db.scan(0);

        List<RedisReply> keyReplies = keys.stream().map(FullBulkStringReply::new).collect(Collectors.toList());
        int size = keyReplies.size();

        List<RedisReply> children = new ArrayList<>();
        children.add(new FullBulkStringReply(String.valueOf(size)));
        children.add(new ArrayReply(keyReplies));
        return new ArrayReply(children);
    }
}
