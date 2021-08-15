package com.lmmmowi.redis.db.aof;

import com.lmmmowi.redis.server.ClientHolder;
import com.lmmmowi.redis.server.ClientInfo;
import com.lmmmowi.redis.server.DefaultServerProcessor;
import com.lmmmowi.redis.server.RedisCommandLine;

import java.util.List;

public class AofResumeProcessor extends DefaultServerProcessor implements AofResumeExecutor {

    private final ClientHolder clientHolder = ClientHolder.getInstance();

    @Override
    public void execute(List<String[]> commands) {
        ClientInfo clientInfo = clientHolder.init("redis aof resume processor");
        clientInfo.setSystemClient(true);

        try {
            for (String[] commandParts : commands) {
                RedisCommandLine redisCommandLine = new RedisCommandLine(commandParts);
                this.process(redisCommandLine);
            }
        } finally {
            clientHolder.clear();
        }
    }
}
