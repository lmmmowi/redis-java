package com.lmmmowi.redis.server;

import lombok.Data;

@Data
public class ClientInfo {

    private static final int DEFAULT_DB_INDEX = 0;

    private String name;
    private int dbIndex;

    ClientInfo(String name) {
        this.name = name;
        this.dbIndex = DEFAULT_DB_INDEX;
    }
}
