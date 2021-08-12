package com.lmmmowi.redis.protocol.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RpushCommand implements RedisCommand {

    private String key;
    private String[] values;
}
