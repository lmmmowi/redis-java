package com.lmmmowi.redis.client.netty;

import com.lmmmowi.redis.protocol.reply.*;
import io.netty.handler.codec.redis.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
class NettyRedisMessageConverter {

    private NettyRedisMessageConverter() {
    }

    public static RedisReply convert(RedisMessage redisMessage) {
        if (redisMessage instanceof SimpleStringRedisMessage) {
            return convert((SimpleStringRedisMessage) redisMessage);
        } else if (redisMessage instanceof ErrorRedisMessage) {
            return convert((ErrorRedisMessage) redisMessage);
        } else if (redisMessage instanceof IntegerRedisMessage) {
            return convert((IntegerRedisMessage) redisMessage);
        } else if (redisMessage instanceof FullBulkStringRedisMessage) {
            return convert((FullBulkStringRedisMessage) redisMessage);
        } else if (redisMessage instanceof ArrayRedisMessage) {
            return convert((ArrayRedisMessage) redisMessage);
        } else {
            log.warn("unkown redis message type: {}", redisMessage.getClass().getName());
            return null;
        }
    }

    private static RedisReply convert(SimpleStringRedisMessage message) {
        return new StatusReply(message.content());
    }

    private static ErrorReply convert(ErrorRedisMessage message) {
        return new ErrorReply(message.content());
    }

    private static IntegerReply convert(IntegerRedisMessage message) {
        return new IntegerReply(message.value());
    }

    private static FullBulkStringReply convert(FullBulkStringRedisMessage message) {
        String content = message.content().toString(CharsetUtil.UTF_8);
        return new FullBulkStringReply(content);
    }

    private static ArrayReply convert(ArrayRedisMessage message) {
        List<RedisReply> children = message.children()
                .stream()
                .map(NettyRedisMessageConverter::convert)
                .collect(Collectors.toList());
        return new ArrayReply(children);
    }
}
