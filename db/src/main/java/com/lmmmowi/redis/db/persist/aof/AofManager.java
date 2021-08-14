package com.lmmmowi.redis.db.persist.aof;

import cn.hutool.core.io.FileUtil;
import com.lmmmowi.redis.protocol.annotation.AOF;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class AofManager {

    private static final AofManager INSTANCE = new AofManager();

    private AofCache aofCache;

    public static AofManager getInstance() {
        return INSTANCE;
    }

    public void init() {
        try {
            File appendFile = new File("data/appendonly.aof");
            log.info("init aof file: {}", appendFile.getAbsolutePath());

            // 创建AOF文件
            FileUtil.mkParentDirs(appendFile);

            aofCache = new AofCache(appendFile);
            aofCache.open();
        } catch (IOException e) {
            throw new IllegalStateException("fail to open aof cache", e);
        }
    }

    public void destroy() {
        if (aofCache != null) {
            aofCache.close();
            log.info("success to close aof file");
        }
    }

    public void append(RedisCommand command) {
        if (this.shouldAppendAof(command)) {
            try {
                aofCache.write(command);
            } catch (IOException e) {
                throw new IllegalStateException("fail to append aof log", e);
            }
        }
    }

    private boolean shouldAppendAof(RedisCommand command) {
        // TODO 做缓存处理，避免每次都进行反射
        AOF aof = command.getClass().getAnnotation(AOF.class);
        return aof != null;
    }
}
