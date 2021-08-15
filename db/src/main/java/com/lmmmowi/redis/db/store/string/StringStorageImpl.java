package com.lmmmowi.redis.db.store.string;

import com.lmmmowi.redis.db.GlobalHashTable;
import com.lmmmowi.redis.db.store.AbstractStorage;
import com.lmmmowi.redis.db.store.StoreType;

public class StringStorageImpl extends AbstractStorage<StringStore> implements StringStorage {

    public StringStorageImpl(GlobalHashTable globalHashTable) {
        super(globalHashTable);
    }

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
