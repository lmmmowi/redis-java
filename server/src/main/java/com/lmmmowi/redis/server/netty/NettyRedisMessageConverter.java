package com.lmmmowi.redis.server.netty;

import com.lmmmowi.redis.server.RedisCommandLine;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class NettyRedisMessageConverter {

    private NettyRedisMessageConverter() {
    }

    public static RedisCommandLine convert(RedisMessage redisMessage) {
        if (redisMessage instanceof ArrayRedisMessage) {
            return convert((ArrayRedisMessage) redisMessage);
        } else {
            log.warn("unkown redis message type: {}", redisMessage.getClass().getName());
            return null;
        }
    }

    private static RedisCommandLine convert(ArrayRedisMessage message) {
        String[] parts = message.children()
                .stream()
                .map(msg -> (FullBulkStringRedisMessage) msg)
                .map(NettyRedisMessageConverter::convert)
                .toArray(String[]::new);
        return new RedisCommandLine(parts);
    }

    private static String convert(FullBulkStringRedisMessage message) {
        return message.content().toString(CharsetUtil.UTF_8);
    }
}
