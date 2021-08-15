package com.lmmmowi.redis.db.aof;

import java.util.List;

public interface AofResumeExecutor {

    void execute(List<String[]> commands);
}
