package com.lmmmowi.redis.db.aof;

import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.util.IoUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

@Slf4j
class AofCache implements AutoCloseable {

    private File appendOnlyFile;
    private Writer writer;
    private AofAppender aofAppender;

    AofCache(File appendOnlyFile) throws IOException {
        this.appendOnlyFile = appendOnlyFile;
        this.writer = new FileWriter(appendOnlyFile, true);
        this.aofAppender = new AofAppender(this);
    }

    @Override
    public void close() {
        IoUtils.close(writer);
        log.info("success to close aof file: {}", appendOnlyFile.getAbsolutePath());
    }

    AofAppender getAppender() {
        return aofAppender;
    }

    void write(RedisCommand command) throws IOException {
        String serializedCommand = command.serialize();
        writer.write(serializedCommand);

        // TODO 暂时采用ALWAYS方式，即每次追加后都执行刷盘操作
        writer.flush();
    }
}
