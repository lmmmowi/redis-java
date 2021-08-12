package com.lmmmowi.redis.db;

public interface Store<T> {

    StoreType getType();

    T getValue();

    void setValue(T value);
}
