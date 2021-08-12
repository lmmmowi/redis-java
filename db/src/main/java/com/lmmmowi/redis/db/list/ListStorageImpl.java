package com.lmmmowi.redis.db.list;

import com.lmmmowi.redis.db.AbstractStorage;
import com.lmmmowi.redis.db.StoreType;

import java.util.Arrays;
import java.util.List;

public class ListStorageImpl extends AbstractStorage<ListStore> implements ListStorage {

    @Override
    protected StoreType getStoreType() {
        return StoreType.LIST;
    }

    @Override
    protected ListStore newStoreInstance() {
        return new ListStore();
    }

    @Override
    public long rpush(String key, String[] values) {
        ListStore store = this.computeIfAbsent(key);
        List<String> list = store.getValue();
        list.addAll(Arrays.asList(values));
        return list.size();
    }

    @Override
    public long lpush(String key, String[] values) {
        ListStore store = this.computeIfAbsent(key);
        List<String> list = store.getValue();
        for (String value : values) {
            list.add(0, value);
        }
        return list.size();
    }
}
