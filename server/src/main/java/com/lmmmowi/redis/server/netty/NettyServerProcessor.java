package com.lmmmowi.redis.server.netty;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.reply.ErrorReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.protocol.reply.StatusReply;
import com.lmmmowi.redis.server.RedisCommandLine;
import com.lmmmowi.redis.server.ServerProcessor;
import com.lmmmowi.redis.server.execute.RedisCommandExecutor;
import com.lmmmowi.redis.server.execute.RedisCommandExecutors;
import com.lmmmowi.redis.server.parser.RedisCommandParseKit;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServerProcessor implements ServerProcessor {

    private final NettyClientHolder nettyClientHolder = NettyClientHolder.getInstance();
    private final RedisCommandParseKit commandParseKit = RedisCommandParseKit.getInstance();
    private final RedisCommandExecutors commandExecutors = RedisCommandExecutors.getInstance();

    @Override
    public void process(RedisCommandLine redisCommandLine) {
        RedisCommand command = commandParseKit.parse(redisCommandLine);

        RedisCommandExecutor executor = commandExecutors.getExecutor(command);
        if (executor != null) {
            RedisReply reply = executor.execute(command);
            this.writeReply(reply);
        } else {
            log.warn("can not find executor for command type: {}", command.getClass().getSimpleName());

            RedisReply reply = new StatusReply("OK");
            this.writeReply(reply);
        }
    }

    private void writeReply(RedisReply redisReply) {
        Channel channel = nettyClientHolder.getClientChannel();
        channel.writeAndFlush(redisReply);
    }
}
