package com.lmmmowi.redis.db.store.string;

public interface StringStorage {

    void set(String key, String value);

    String get(String key);
}
