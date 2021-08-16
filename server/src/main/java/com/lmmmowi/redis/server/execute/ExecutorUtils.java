package com.lmmmowi.redis.server.execute;

import com.lmmmowi.redis.db.DbInstance;
import com.lmmmowi.redis.db.RedisDb;
import com.lmmmowi.redis.db.store.DataStorage;
import com.lmmmowi.redis.db.store.list.ListStorage;
import com.lmmmowi.redis.db.store.string.StringStorage;
import com.lmmmowi.redis.server.RedisServerRuntime;
import com.lmmmowi.redis.server.client.ClientInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ExecutorUtils {

    static DataStorage getStorage(Class<? extends DataStorage> dataStorageType) {
        DbInstance db = currentDbInstance();
        if (StringStorage.class.equals(dataStorageType)) {
            return db.getStringStorage();
        } else if (ListStorage.class.equals(dataStorageType)) {
            return db.getListStorage();
        } else {
            return null;
        }
    }

    static RedisDb getRedisDb() {
        RedisServerRuntime runtime = RedisServerRuntime.get();
        return runtime.getRedisDb();
    }

    static DbInstance currentDbInstance() {
        ClientInfo clientInfo = currentClient();
        int dbIndex = clientInfo.getDbIndex();
        return getRedisDb().select(dbIndex);
    }

    static ClientInfo currentClient() {
        RedisServerRuntime runtime = RedisServerRuntime.get();
        return runtime.getClientHolder().getClientInfo();
    }
}
