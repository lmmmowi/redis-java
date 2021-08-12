package com.lmmmowi.redis.db.list;

public interface ListStorage {

    long rpush(String key, String[] values);

    long lpush(String key, String[] values);
}
