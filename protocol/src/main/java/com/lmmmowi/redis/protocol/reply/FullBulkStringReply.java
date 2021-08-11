package com.lmmmowi.redis.protocol.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FullBulkStringReply implements RedisReply {

    private String content;
}
