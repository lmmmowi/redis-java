package com.lmmmowi.redis.configuration;

import lombok.Data;

@Data
public class RedisConfiguration {

    private ServerConfiguration server;

    private AofConfiguration aof;
}
