package com.lmmmowi.redis.protocol.command;

import lombok.AllArgsConstructor;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class UnkownCommand implements RedisCommand {

    private String[] parts;

    public String getError() {
        String command = parts[0];
        String args = IntStream.range(1, parts.length)
                .boxed()
                .map(i -> String.format("`%s`,", parts[i]))
                .collect(Collectors.joining(" "));
        return String.format("ERR unknown command `%s`,with args beginning with: %s", command, args);
    }
}
