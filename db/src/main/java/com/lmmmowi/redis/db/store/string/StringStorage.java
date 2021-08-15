package com.lmmmowi.redis.db.store.string;

import com.lmmmowi.redis.db.store.DataStorage;

public interface StringStorage extends DataStorage {

    void set(String key, String value);

    String get(String key);
}
