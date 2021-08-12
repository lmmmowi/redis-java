package com.lmmmowi.redis.db;

import com.lmmmowi.redis.db.list.ListStorage;
import com.lmmmowi.redis.db.string.StringStorage;
import com.lmmmowi.redis.db.string.StringStorageImpl;

public class RedisDb {

    private static final RedisDb INSTANCE = new RedisDb();

    private StringStorage stringStorage = new StringStorageImpl();

    private RedisDb() {
    }

    public static RedisDb getInstance() {
        return INSTANCE;
    }

    public StringStorage getStringStorage() {
        return this.stringStorage;
    }

    public ListStorage getListStorage() {
        return null;
    }
}
