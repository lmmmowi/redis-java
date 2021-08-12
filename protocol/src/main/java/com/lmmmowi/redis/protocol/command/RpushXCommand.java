package com.lmmmowi.redis.protocol.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RpushXCommand implements RedisCommand {

    private String key;
    private String[] values;

}
