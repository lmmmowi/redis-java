package com.lmmmowi.redis.db.aof;

import cn.hutool.core.io.IoUtil;
import com.lmmmowi.redis.protocol.command.RedisCommand;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class AofCache {

    private File appendFile;
    private Writer writer;

    public AofCache(File appendFile) {
        this.appendFile = appendFile;
    }

    public void open() throws IOException {
        writer = new FileWriter(appendFile, true);
    }

    public void close() {
        IoUtil.close(writer);
    }

    public void write(RedisCommand command) throws IOException {
        String serializedCommand = command.serialize();
        writer.write(serializedCommand);

        // TODO 暂时采用ALWAYS方式，即每次追加后都执行刷盘操作
        writer.flush();
    }
}
