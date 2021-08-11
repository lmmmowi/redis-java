package com.lmmmowi.redis.protocol.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ArrayReply implements RedisReply {

    private List<RedisReply> children;
}
