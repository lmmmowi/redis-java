package com.lmmmowi.redis.db.aof;

import com.lmmmowi.redis.configuration.AofConfiguration;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.util.FileUtils;
import com.lmmmowi.redis.util.IoUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class AofManager {

    private AofConfiguration configuration;

    private File appendOnlyFile;
    private AofCache aofCache;

    public AofManager(AofConfiguration configuration) {
        this.configuration = configuration;
    }

    public void init() {
        appendOnlyFile = new File(configuration.getAppendOnlyFilePath());
        log.info("init aof: {}", appendOnlyFile.getAbsolutePath());

        try {
            FileUtils.mkParentDirs(appendOnlyFile);
            aofCache = new AofCache(appendOnlyFile);
        } catch (IOException e) {
            throw new IllegalStateException("fail to init aof", e);
        }
    }

    public void destroy() {
        IoUtils.close(aofCache);
    }

    public void append(int dbIndex, RedisCommand command) {
        if (!Boolean.TRUE.equals(configuration.getEnabled())) {
            return;
        }

        try {
            AofAppender aofAppender = aofCache.getAppender();
            aofAppender.append(dbIndex, command);
        } catch (IOException e) {
            throw new IllegalStateException("fail to append aof log", e);
        }
    }

    public void resumeData() {
        try {
            log.info("resume data by aof");
            new AofResumeProcessor(appendOnlyFile).run();
        } catch (IOException e) {
            throw new IllegalStateException("fail to data from aof file", e);
        }
    }
}
