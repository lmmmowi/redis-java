package com.lmmmowi.redis.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RedisCommandLine {

    private String[] parts;

    @Override
    public String toString() {
        return String.join(" ", parts);
    }
}
