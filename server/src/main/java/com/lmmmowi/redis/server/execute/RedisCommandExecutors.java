package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.util.ClassUtils;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RedisCommandExecutors {

    private static final RedisCommandExecutors INSTANCE = new RedisCommandExecutors();

    private final Map<Class<? extends RedisCommand>, RedisCommandExecutor> executorMap;

    private RedisCommandExecutors() {
        this.executorMap = ClassUtils.scan(getClass().getPackage(), RedisCommandExecutor.class)
                .stream()
                .map(ClassUtils::newInstance)
                .collect(Collectors.toMap(
                        RedisCommandExecutor::getSupportCommandType,
                        Function.identity()
                ));
    }

    public static RedisCommandExecutors getInstance() {
        return INSTANCE;
    }

    public RedisCommandExecutor getExecutor(RedisCommand command) {
        return executorMap.get(command.getClass());
    }
}
