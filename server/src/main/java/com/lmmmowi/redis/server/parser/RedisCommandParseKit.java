package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.commandline.RedisCommandLine;
import com.lmmmowi.redis.server.exception.CommandParseException;
import com.lmmmowi.redis.server.exception.UnkownCommandException;
import com.lmmmowi.redis.util.ArrayUtils;
import com.lmmmowi.redis.util.ClassUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisCommandParseKit {

    private final Map<String, RedisCommandParser> parserMap;

    public static RedisCommandParseKit create() {
        // 在本包路径下扫描命令解析器类型
        Package scanPackage = RedisCommandParseKit.class.getPackage();
        Collection<Class<RedisCommandParser>> parserClasses = ClassUtils.scan(scanPackage, RedisCommandParser.class);

        // 实例化命令解析器，并以 <支持的解析命令前缀, 命令解析器> 的形式存储在map中
        Map<String, RedisCommandParser> parserMap = parserClasses.stream().map(ClassUtils::newInstance).collect(Collectors.toMap(
                parser -> parser.getCommandKey().toUpperCase(),
                Function.identity()
        ));

        return new RedisCommandParseKit(parserMap);
    }

    public RedisCommand parse(RedisCommandLine commandLine) throws CommandParseException {
        RedisCommandParser redisCommandParser = this.match(commandLine);
        if (redisCommandParser == null) {
            throw new UnkownCommandException(commandLine);
        }

        RedisCommand command = redisCommandParser.parse(commandLine);
        command.setRawParts(commandLine.getParts());
        return command;
    }

    private RedisCommandParser match(RedisCommandLine redisCommandLine) {
        String[] parts = redisCommandLine.getParts();
        String commandKey = ArrayUtils.isEmpty(parts) ? null : parts[0].toUpperCase();
        return parserMap.get(commandKey);
    }
}
