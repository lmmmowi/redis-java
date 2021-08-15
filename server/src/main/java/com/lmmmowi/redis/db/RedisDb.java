package com.lmmmowi.redis.db;

import com.lmmmowi.redis.db.aof.AofManager;
import com.lmmmowi.redis.db.aof.AofResumeProcessor;

import java.security.InvalidParameterException;

public class RedisDb {

    private static final int DB_NUM = 16;

    private static final RedisDb INSTANCE = new RedisDb();

    private DbInstance[] instances = new DbInstance[DB_NUM];

    private RedisDb() {
    }

    public static RedisDb getInstance() {
        return INSTANCE;
    }

    public void init() {
        for (int i = 0; i < instances.length; i++) {
            instances[i] = new DbInstanceImpl();
        }

        AofManager.getInstance().init(new AofResumeProcessor());
    }

    public void destroy() {
        AofManager.getInstance().destroy();
    }

    public DbInstance select(int dbIndex) {
        if (dbIndex >= instances.length || dbIndex < 0) {
            throw new InvalidParameterException("invalid db index");
        }
        return instances[dbIndex];
    }
}
