package com.lmmmowi.redis.server.netty;

import com.lmmmowi.redis.server.RedisCommandLine;
import com.lmmmowi.redis.server.ClientInfo;
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

    private final ServerProcessor serverProcessor;
    private final NettyClientHolder nettyClientHolder = NettyClientHolder.getInstance();

    public NettyServerChannelHandler(ServerProcessor serverProcessor) {
        this.serverProcessor = serverProcessor;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        nettyClientHolder.put(channel, new ClientInfo());

        if (log.isDebugEnabled()) {
            log.debug("new client connected.");
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();
        ClientInfo clientInfo = nettyClientHolder.get(channel);
        nettyClientHolder.setCurrent(clientInfo);

        try {
            RedisMessage redisMessage = (RedisMessage) msg;
            RedisCommandLine redisCommandLine = NettyRedisMessageConverter.convert(redisMessage);

            if (log.isDebugEnabled()) {
                log.debug("receive command: {}", redisCommandLine);
            }

            if (serverProcessor != null) {
                serverProcessor.process(redisCommandLine);
            }
        } finally {
            nettyClientHolder.reset();
            ReferenceCountUtil.release(msg);
        }
    }
}
