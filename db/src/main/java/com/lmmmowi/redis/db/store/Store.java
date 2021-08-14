package com.lmmmowi.redis.db.store;

public interface Store<T> {

    StoreType getType();

    T getValue();

    void setValue(T value);
}
