package com.lmmmowi.redis.db;

import com.lmmmowi.redis.db.store.list.ListStorage;
import com.lmmmowi.redis.db.store.string.StringStorage;

public interface DbInstance {

    StringStorage getStringStorage();

    ListStorage getListStorage();
}
