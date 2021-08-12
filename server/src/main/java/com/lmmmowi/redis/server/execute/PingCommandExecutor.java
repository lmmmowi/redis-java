package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.protocol.command.PingCommand;
import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.protocol.reply.StatusReply;

class PingCommandExecutor extends AbstractCommandExecutor<PingCommand> {

    @Override
    protected RedisReply doExecute(PingCommand command) {
        return new StatusReply("PONG");
    }
}
