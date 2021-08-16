package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.util.ClassUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisCommandExecutors {

    private final Map<Class<? extends RedisCommand>, RedisCommandExecutor> executorMap;

    public static RedisCommandExecutors create() {
        // 在本包路径下扫描命令执行器类型
        Package scanPackage = RedisCommandExecutors.class.getPackage();
        Collection<Class<RedisCommandExecutor>> executorClasses = ClassUtils.scan(scanPackage, RedisCommandExecutor.class);

        // 实例化命令执行器，并以 <支持的命令类型, 命令执行器> 的形式存储在map中
        Map<Class<? extends RedisCommand>, RedisCommandExecutor> executorMap = executorClasses.stream().map(ClassUtils::newInstance).collect(Collectors.toMap(
                RedisCommandExecutor::getSupportCommandType,
                Function.identity()
        ));

        return new RedisCommandExecutors(executorMap);
    }

    public RedisCommandExecutor match(RedisCommand command) {
        return executorMap.get(command.getClass());
    }
}
