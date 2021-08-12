package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.command.UnkownCommand;
import com.lmmmowi.redis.server.RedisCommandLine;
import com.lmmmowi.redis.server.util.ClassUtils;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RedisCommandParseKit {

    private static final RedisCommandParseKit INSTANCE = new RedisCommandParseKit();

    private final Map<String, RedisCommandParser> parserMap;

    private RedisCommandParseKit() {
        this.parserMap = ClassUtils.scan(getClass().getPackage(), RedisCommandParser.class)
                .stream()
                .map(ClassUtils::newInstance)
                .collect(Collectors.toMap(
                        RedisCommandParser::getCommandKey,
                        Function.identity()
                ));
    }

    public static RedisCommandParseKit getInstance() {
        return INSTANCE;
    }

    public RedisCommand parse(RedisCommandLine redisCommandLine) {
        RedisCommandParser redisCommandParser = this.match(redisCommandLine);
        if (redisCommandParser != null) {
            return redisCommandParser.parse(redisCommandLine);
        } else {
            return new UnkownCommand(redisCommandLine.getParts());
        }
    }

    private RedisCommandParser match(RedisCommandLine redisCommandLine) {
        String[] parts = redisCommandLine.getParts();
        String commandKey = parts != null && parts.length > 0 ? parts[0].toLowerCase() : null;
        return parserMap.get(commandKey);
    }
}
