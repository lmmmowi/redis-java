package com.lmmmowi.redis.db.store.list;

import com.lmmmowi.redis.db.store.Store;
import com.lmmmowi.redis.db.store.StoreType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ListStore implements Store<List<String>> {

    @Setter
    @Getter
    private List<String> value;

    public ListStore() {
        this.value = new ArrayList<>();
    }

    @Override
    public StoreType getType() {
        return StoreType.LIST;
    }
}
