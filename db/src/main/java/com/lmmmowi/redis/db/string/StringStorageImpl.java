package com.lmmmowi.redis.db.string;

import com.lmmmowi.redis.db.GlobalHashTable;
import com.lmmmowi.redis.db.Store;
import com.lmmmowi.redis.db.StoreType;

public class StringStorageImpl implements StringStorage {

    private final GlobalHashTable globalHashTable = GlobalHashTable.getInstance();

    @Override
    public void set(String key, String value) {
        Store store = globalHashTable.getStore(key, StoreType.STRING);
        if (store == null) {
            store = new Store(StoreType.STRING);
            globalHashTable.setStore(key, store);
        }

        store.setValue(value);
    }

    @Override
    public String get(String key) {
        Store store = globalHashTable.getStore(key, StoreType.STRING);
        if (store == null) {
            return null;
        }

        return (String) store.getValue();
    }
}
