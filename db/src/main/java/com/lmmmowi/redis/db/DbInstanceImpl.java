package com.lmmmowi.redis.db;

import com.lmmmowi.redis.db.store.list.ListStorage;
import com.lmmmowi.redis.db.store.list.ListStorageImpl;
import com.lmmmowi.redis.db.store.string.StringStorage;
import com.lmmmowi.redis.db.store.string.StringStorageImpl;

import java.util.Collection;

public class DbInstanceImpl implements DbInstance {

    private final GlobalHashTable globalHashTable = new GlobalHashTable();
    private final StringStorage stringStorage = new StringStorageImpl(globalHashTable);
    private final ListStorage listStorage = new ListStorageImpl(globalHashTable);

    @Override
    public Collection<String> keys(String pattern) {
        return globalHashTable.keys(pattern);
    }

    @Override
    public Collection<String> scan(int count) {
        return globalHashTable.scan(count);
    }

    @Override
    public StringStorage getStringStorage() {
        return this.stringStorage;
    }

    @Override
    public ListStorage getListStorage() {
        return this.listStorage;
    }
}
