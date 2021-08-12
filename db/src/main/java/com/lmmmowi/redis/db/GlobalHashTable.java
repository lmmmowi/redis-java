package com.lmmmowi.redis.db;

import java.util.HashMap;
import java.util.Map;

public class GlobalHashTable {

    private static final GlobalHashTable INSTANCE = new GlobalHashTable();

    private Map<String, Store> map = new HashMap<>();

    private GlobalHashTable() {
    }

    public static GlobalHashTable getInstance() {
        return INSTANCE;
    }

    public Store getStore(String key) {
        return map.get(key);
    }

    public Store setStore(String key, Store store) {
        Store existStore = this.getStore(key);
        if (existStore != null) {
            throw new IllegalStateException("duplicate store key");
        }

        return map.put(key, store);
    }

    public void remove(String key) {
        map.remove(key);
    }
}
