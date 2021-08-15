package com.lmmmowi.redis.db.store;

import com.lmmmowi.redis.db.GlobalHashTable;
import com.lmmmowi.redis.db.exception.WrongTypeOperationException;

public abstract class AbstractStorage<T extends Store> implements DataStorage {

    protected final GlobalHashTable globalHashTable;

    public AbstractStorage(GlobalHashTable globalHashTable) {
        this.globalHashTable = globalHashTable;
    }

    protected abstract StoreType getStoreType();

    protected abstract T newStoreInstance();

    protected T getStore(String key) {
        StoreType storeType = this.getStoreType();
        Store store = globalHashTable.getStore(key);
        if (store != null) {
            if (!storeType.equals(store.getType())) {
                throw new WrongTypeOperationException();
            }
        }
        return (T) store;
    }

    protected T createStore(String key) {
        T store = this.newStoreInstance();
        globalHashTable.setStore(key, store);
        return store;
    }

    protected T computeIfAbsent(String key) {
        T store = this.getStore(key);
        if (store == null) {
            store = this.createStore(key);
        }
        return store;
    }
}
