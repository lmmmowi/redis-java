package com.lmmmowi.redis.server.commandline;

import com.lmmmowi.redis.db.exception.DbOperationException;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.reply.ErrorReply;
import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.server.exception.CommandParseException;
import com.lmmmowi.redis.server.exception.UnkownCommandException;
import com.lmmmowi.redis.server.execute.RedisCommandExecutor;
import com.lmmmowi.redis.server.execute.RedisCommandExecutors;
import com.lmmmowi.redis.server.parser.RedisCommandParseKit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultCommandLineProcessor implements CommandLineProcessor {

    private final RedisCommandParseKit commandParseKit;
    private final RedisCommandExecutors commandExecutors;

    public static DefaultCommandLineProcessor create() {
        RedisCommandParseKit commandParseKit = RedisCommandParseKit.create();
        RedisCommandExecutors commandExecutors = RedisCommandExecutors.create();
        return new DefaultCommandLineProcessor(commandParseKit, commandExecutors);
    }

    @Override
    public RedisReply process(RedisCommandLine commandLine) {
        try {
            // 解析命令
            RedisCommand command = commandParseKit.parse(commandLine);

            // 匹配命令执行器
            RedisCommandExecutor executor = commandExecutors.match(command);
            if (executor == null) {
                log.warn("can not find executor for command type: {}", command.getClass().getSimpleName());
                throw new UnkownCommandException(commandLine);
            }

            // 执行命令
            return executor.execute(command);
        } catch (CommandParseException | DbOperationException e) {
            return new ErrorReply(e.getMessage());
        }
    }
}
