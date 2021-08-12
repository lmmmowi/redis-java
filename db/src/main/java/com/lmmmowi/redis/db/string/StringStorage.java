package com.lmmmowi.redis.db.string;

public interface StringStorage {

    void set(String key, String value);

    String get(String key);
}
