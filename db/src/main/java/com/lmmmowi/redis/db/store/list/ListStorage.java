package com.lmmmowi.redis.db.store.list;

import com.lmmmowi.redis.db.store.DataStorage;

public interface ListStorage extends DataStorage {

    long lpush(String key, String[] values);

    long lpushx(String key, String[] values);

    long rpush(String key, String[] values);

    long rpushx(String key, String[] values);

}
