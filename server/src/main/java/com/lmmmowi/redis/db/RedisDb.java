package com.lmmmowi.redis.db;

import com.lmmmowi.redis.configuration.AofConfiguration;
import com.lmmmowi.redis.db.aof.AofManager;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.InvalidParameterException;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisDb {

    private static final int DB_NUM = 16;

    private DbInstance[] instances;

    @Getter
    private AofManager aofManager;

    public static RedisDb create(AofConfiguration configuration) {
        // 创建数据库示例
        DbInstance[] instances = new DbInstance[DB_NUM];
        for (int i = 0; i < instances.length; i++) {
            instances[i] = new DbInstanceImpl();
        }

        // 初始化AOF机制
        AofManager aofManager = new AofManager(configuration);

        return new RedisDb(instances, aofManager);
    }

    public void init() {
        aofManager.init();
    }

    public void destroy() {
        aofManager.destroy();
    }

    public DbInstance select(int dbIndex) {
        if (dbIndex >= instances.length || dbIndex < 0) {
            throw new InvalidParameterException("invalid db index");
        }
        return instances[dbIndex];
    }

    public void resumeData() {
        // 通过AOF恢复数据
        aofManager.resumeData();
    }
}
