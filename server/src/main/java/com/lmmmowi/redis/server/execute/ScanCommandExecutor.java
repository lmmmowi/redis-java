package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.protocol.command.ScanCommand;
import com.lmmmowi.redis.protocol.reply.ArrayReply;
import com.lmmmowi.redis.protocol.reply.FullBulkStringReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanCommandExecutor implements RedisCommandExecutor<ScanCommand> {

    @Override
    public Class<ScanCommand> getSupportCommandType() {
        return ScanCommand.class;
    }

    @Override
    public RedisReply execute(ScanCommand command) {
        List<RedisReply> children = new ArrayList<>();
        children.add(new FullBulkStringReply("0"));
        children.add(new ArrayReply(Collections.emptyList()));
        return new ArrayReply(children);
    }
}
