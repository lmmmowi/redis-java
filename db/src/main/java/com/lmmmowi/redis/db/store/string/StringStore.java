package com.lmmmowi.redis.db.store.string;

import com.lmmmowi.redis.db.store.Store;
import com.lmmmowi.redis.db.store.StoreType;
import lombok.Getter;
import lombok.Setter;

public class StringStore implements Store<String> {

    @Getter
    @Setter
    private String value;

    @Override
    public StoreType getType() {
        return StoreType.STRING;
    }
}
