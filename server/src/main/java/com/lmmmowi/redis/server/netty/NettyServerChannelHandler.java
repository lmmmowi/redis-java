package com.lmmmowi.redis.server.netty;

import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.server.ClientHolder;
import com.lmmmowi.redis.server.ClientInfo;
import com.lmmmowi.redis.server.RedisCommandLine;
import com.lmmmowi.redis.server.ServerProcessor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServerChannelHandler extends ChannelDuplexHandler {

    private final ClientHolder clientHolder = ClientHolder.getInstance();
    private final ServerProcessor serverProcessor;

    public NettyServerChannelHandler(ServerProcessor serverProcessor) {
        this.serverProcessor = serverProcessor;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String remoteAddress = channel.remoteAddress().toString();
        ClientInfo clientInfo = clientHolder.init(remoteAddress);

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
            RedisCommandLine redisCommandLine = NettyRedisMessageConverter.convert(redisMessage);

            if (log.isDebugEnabled()) {
                log.debug("receive command from channel[{}]: {}", channel.id(), redisCommandLine);
            }

            if (serverProcessor != null) {
                RedisReply redisReply = serverProcessor.process(redisCommandLine);
                if (redisReply != null) {
                    channel.writeAndFlush(redisReply);
                }
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
