package com.lmmmowi.redis.db;

import com.lmmmowi.redis.db.store.Store;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GlobalHashTable {

    private Map<String, Store> map = new HashMap<>();

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

    public Collection<String> keys(String patternStr) {
        String regex = patternStr.replace("*", ".*");
        Pattern pattern = Pattern.compile(regex);

        return map.keySet()
                .stream()
                .filter(s -> pattern.matcher(s).matches())
                .collect(Collectors.toList());
    }

    public Collection<String> scan(int count) {
        return map.keySet();
    }
}
