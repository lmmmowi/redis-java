package com.lmmmowi.redis.db.store.list;

public interface ListStorage {

    long lpush(String key, String[] values);

    long lpushx(String key, String[] values);

    long rpush(String key, String[] values);

    long rpushx(String key, String[] values);

}
