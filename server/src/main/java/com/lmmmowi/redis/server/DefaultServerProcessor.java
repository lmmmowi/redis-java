package com.lmmmowi.redis.server;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.protocol.reply.StatusReply;
import com.lmmmowi.redis.server.execute.RedisCommandExecutor;
import com.lmmmowi.redis.server.execute.RedisCommandExecutors;
import com.lmmmowi.redis.server.parser.RedisCommandParseKit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultServerProcessor implements ServerProcessor {

    private final RedisCommandParseKit commandParseKit = RedisCommandParseKit.getInstance();
    private final RedisCommandExecutors commandExecutors = RedisCommandExecutors.getInstance();

    @Override
    public RedisReply process(RedisCommandLine redisCommandLine) {
        RedisCommand command = commandParseKit.parse(redisCommandLine);
        RedisCommandExecutor executor = commandExecutors.getExecutor(command);
        if (executor != null) {
            return executor.execute(command);
        } else {
            log.warn("can not find executor for command type: {}", command.getClass().getSimpleName());
            return new StatusReply("OK");
        }
    }
}
