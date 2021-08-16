package com.lmmmowi.redis.db.aof;

import com.lmmmowi.redis.protocol.annotation.AOF;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.protocol.command.SelectCommand;

import java.io.IOException;

class AofAppender {

    private final AofCache aofCache;
    private Integer selectedDbIndex;

    AofAppender(AofCache aofCache) {
        this.aofCache = aofCache;
    }

    void append(int dbIndex, RedisCommand command) throws IOException {
        if (!this.shouldAppendAof(command)) {
            return;
        }

        if (selectedDbIndex == null || !selectedDbIndex.equals(dbIndex)) {
            RedisCommand selectCommand = new SelectCommand(dbIndex);
            aofCache.write(selectCommand);
            selectedDbIndex = dbIndex;
        }

        aofCache.write(command);
    }

    private boolean shouldAppendAof(RedisCommand command) {
        // TODO 做缓存处理，避免每次都进行反射
        AOF aof = command.getClass().getAnnotation(AOF.class);
        return aof != null;
    }
}
