package com.lmmmowi.redis.protocol.reply;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorReply implements RedisReply {

    private String content;

    public String getContent() {
        return "ERR " + content;
    }
}
