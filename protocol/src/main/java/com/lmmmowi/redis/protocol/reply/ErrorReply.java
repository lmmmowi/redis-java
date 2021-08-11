package com.lmmmowi.redis.protocol.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorReply implements RedisReply {

    private String content;
}
