package com.lmmmowi.redis.server.netty;

import com.lmmmowi.redis.protocol.reply.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
class NettyRedisMessageConverter {

    private NettyRedisMessageConverter() {
    }

    static String[] convert(RedisMessage redisMessage) {
        ArrayRedisMessage arrayRedisMessage = (ArrayRedisMessage) redisMessage;
        return arrayRedisMessage.children()
                .stream()
                .map(FullBulkStringRedisMessage.class::cast)
                .map(msg -> msg.content().toString(CharsetUtil.UTF_8))
                .toArray(String[]::new);
    }

    static RedisMessage convert(RedisReply redisReply, ChannelHandlerContext ctx) {
        if (redisReply instanceof StatusReply) {
            return new SimpleStringRedisMessage(((StatusReply) redisReply).getContent());
        } else if (redisReply instanceof ErrorReply) {
            return new ErrorRedisMessage(((ErrorReply) redisReply).getContent());
        } else if (redisReply instanceof IntegerReply) {
            return new IntegerRedisMessage(((IntegerReply) redisReply).getValue());
        } else if (redisReply instanceof FullBulkStringReply) {
            FullBulkStringReply fullBulkStringReply = (FullBulkStringReply) redisReply;
            String content = fullBulkStringReply.getContent();
            if (content == null) {
                return FullBulkStringRedisMessage.NULL_INSTANCE;
            } else {
                ByteBuf byteBuf = ByteBufUtil.writeUtf8(ctx.alloc(), content);
                return new FullBulkStringRedisMessage(byteBuf);
            }
        } else if (redisReply instanceof ArrayReply) {
            ArrayReply arrayReply = (ArrayReply) redisReply;
            List<RedisMessage> children = arrayReply.getChildren()
                    .stream()
                    .map(o -> convert(o, ctx))
                    .collect(Collectors.toList());
            return new ArrayRedisMessage(children);
        } else {
            return null;
        }
    }
}
