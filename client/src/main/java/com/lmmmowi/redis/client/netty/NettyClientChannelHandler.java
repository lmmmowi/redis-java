package com.lmmmowi.redis.client.netty;

import com.lmmmowi.redis.client.ClientProcessor;
import com.lmmmowi.redis.protocol.reply.RedisReply;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.ReferenceCountUtil;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
public class NettyClientChannelHandler extends ChannelDuplexHandler {

    private ClientProcessor clientProcessor;
    private Consumer<Channel> channelConsumer;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        channelConsumer.accept(ctx.channel());
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        String[] commands = ((String) msg).split("\\s+");
        List<RedisMessage> children = new ArrayList<>(commands.length);
        for (String cmdString : commands) {
            children.add(new FullBulkStringRedisMessage(ByteBufUtil.writeUtf8(ctx.alloc(), cmdString)));
        }
        RedisMessage request = new ArrayRedisMessage(children);
        ctx.write(request, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            RedisMessage redisMessage = (RedisMessage) msg;
            RedisReply redisReply = NettyRedisMessageConverter.convert(redisMessage);

            if (clientProcessor != null) {
                clientProcessor.process(redisReply);
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
