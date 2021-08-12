package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.command.UnkownCommand;
import com.lmmmowi.redis.server.RedisCommandLine;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RedisCommandParseKit {

    private static final List<RedisCommandParser> SUPPORTED_PARSERS = Arrays.asList(
            new PingCommandParser(),
            new ClientCommandParser(),
            new InfoCommandParser(),
            new ScanCommandParser()
    );

    private static final RedisCommandParseKit INSTANCE = new RedisCommandParseKit();

    private final Map<String, RedisCommandParser> parserMap;

    private RedisCommandParseKit() {
        this.parserMap = SUPPORTED_PARSERS.stream().collect(Collectors.toMap(
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
