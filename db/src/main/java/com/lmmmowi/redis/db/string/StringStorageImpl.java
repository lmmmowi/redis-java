package com.lmmmowi.redis.db.string;

import com.lmmmowi.redis.db.GlobalHashTable;
import com.lmmmowi.redis.db.Store;
import com.lmmmowi.redis.db.StoreType;
import com.lmmmowi.redis.db.exception.WrongTypeOperationException;

public class StringStorageImpl implements StringStorage {

    private final GlobalHashTable globalHashTable = GlobalHashTable.getInstance();

    @Override
    public void set(String key, String value) {
        Store store = globalHashTable.getStore(key);
        if (store == null) {
            store = new Store(StoreType.STRING);
        }

        if (!StoreType.STRING.equals(store.getType())) {
            throw new WrongTypeOperationException();
        }

        store.setValue(value);
    }

    @Override
    public String get(String key) {
        Store store = globalHashTable.getStore(key);
        if (store == null) {
            return null;
        }

        if (!StoreType.STRING.equals(store.getType())) {
            throw new WrongTypeOperationException();
        }

        return (String) store.getValue();
    }
}
