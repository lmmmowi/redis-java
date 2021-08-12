package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.protocol.command.PingCommand;
import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.protocol.reply.StatusReply;

public class PingCommandExecutor implements RedisCommandExecutor<PingCommand> {

    @Override
    public Class<PingCommand> getSupportCommandType() {
        return PingCommand.class;
    }

    @Override
    public RedisReply execute(PingCommand command) {
        return new StatusReply("PONG");
    }
}
