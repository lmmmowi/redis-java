package com.lmmmowi.redis.protocol.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IntegerReply implements RedisReply {

    private long value;
}
