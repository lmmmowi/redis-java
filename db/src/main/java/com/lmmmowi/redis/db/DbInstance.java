package com.lmmmowi.redis.db;

import com.lmmmowi.redis.db.store.list.ListStorage;
import com.lmmmowi.redis.db.store.string.StringStorage;

import java.util.Collection;

public interface DbInstance {

    Collection<String> keys(String pattern);

    Collection<String> scan(int count);

    StringStorage getStringStorage();

    ListStorage getListStorage();
}
