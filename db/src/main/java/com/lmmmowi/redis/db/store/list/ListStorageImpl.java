package com.lmmmowi.redis.db.store.list;

import com.lmmmowi.redis.db.GlobalHashTable;
import com.lmmmowi.redis.db.store.AbstractStorage;
import com.lmmmowi.redis.db.store.StoreType;

import java.util.Arrays;
import java.util.List;

public class ListStorageImpl extends AbstractStorage<ListStore> implements ListStorage {

    public ListStorageImpl(GlobalHashTable globalHashTable) {
        super(globalHashTable);
    }

    @Override
    protected StoreType getStoreType() {
        return StoreType.LIST;
    }

    @Override
    protected ListStore newStoreInstance() {
        return new ListStore();
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

    @Override
    public long lpushx(String key, String[] values) {
        ListStore store = this.getStore(key);
        return store == null ? 0L : this.lpush(key, values);
    }

    @Override
    public long rpush(String key, String[] values) {
        ListStore store = this.computeIfAbsent(key);
        List<String> list = store.getValue();
        list.addAll(Arrays.asList(values));
        return list.size();
    }

    @Override
    public long rpushx(String key, String[] values) {
        ListStore store = this.getStore(key);
        return store == null ? 0L : this.rpush(key, values);
    }

}
