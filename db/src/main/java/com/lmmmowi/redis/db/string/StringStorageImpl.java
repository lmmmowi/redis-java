package com.lmmmowi.redis.db.string;

import com.lmmmowi.redis.db.AbstractStorage;
import com.lmmmowi.redis.db.StoreType;

public class StringStorageImpl extends AbstractStorage<StringStore> implements StringStorage {

    @Override
    protected StoreType getStoreType() {
        return StoreType.STRING;
    }

    @Override
    protected StringStore newStoreInstance() {
        return new StringStore();
    }

    @Override
    public void set(String key, String value) {
        StringStore store = this.computeIfAbsent(key);
        store.setValue(value);
    }

    @Override
    public String get(String key) {
        StringStore store = this.getStore(key);
        return store == null ? null : store.getValue();
    }
}
