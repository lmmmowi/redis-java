package com.lmmmowi.redis.db;

import lombok.Data;

@Data
public class Store {

    private StoreType type;
    private Object value;

    public Store(StoreType type) {
        this.type = type;
    }
}
