package com.lmmmowi.redis.server.netty;

import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.server.RedisServerRuntime;
import com.lmmmowi.redis.server.client.ClientHolder;
import com.lmmmowi.redis.server.client.ClientInfo;
import com.lmmmowi.redis.server.commandline.CommandLineProcessor;
import com.lmmmowi.redis.server.commandline.RedisCommandLine;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class NettyServerChannelHandler extends ChannelDuplexHandler {

    private final ClientHolder clientHolder;
    private final CommandLineProcessor commandLineProcessor;

    NettyServerChannelHandler() {
        RedisServerRuntime runtime = RedisServerRuntime.get();
        clientHolder = runtime.getClientHolder();
        commandLineProcessor = RedisServerRuntime.get().getCommandLineProcessor();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 为新建客户端连接创建对应的信息存储
        Channel channel = ctx.channel();
        String remoteAddress = channel.remoteAddress().toString();
        ClientInfo clientInfo = clientHolder.newClient(remoteAddress);

        if (log.isDebugEnabled()) {
            log.debug("new client[{}] connected.", clientInfo);
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        RedisReply redisReply = (RedisReply) msg;
        RedisMessage redisMessage = NettyRedisMessageConverter.convert(redisReply, ctx);
        if (redisMessage != null) {
            ctx.write(redisMessage, promise);

            if (log.isDebugEnabled()) {
                Channel channel = ctx.channel();
                log.debug("write reply to channel[{}]: {}", channel.id(), redisMessage);
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();

        try {
            RedisMessage redisMessage = (RedisMessage) msg;
            String[] commandParts = NettyRedisMessageConverter.convert(redisMessage);

            if (log.isDebugEnabled()) {
                log.debug("receive command from channel[{}]: {}", channel.id(), String.join(" ", commandParts));
            }

            if (commandLineProcessor != null) {
                RedisCommandLine commandLine = new RedisCommandLine(commandParts);
                RedisReply redisReply = commandLineProcessor.process(commandLine);
                if (redisReply != null) {
                    channel.writeAndFlush(redisReply);
                }
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
