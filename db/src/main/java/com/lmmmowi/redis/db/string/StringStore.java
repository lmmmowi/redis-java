package com.lmmmowi.redis.db.string;

import com.lmmmowi.redis.db.Store;
import com.lmmmowi.redis.db.StoreType;
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
