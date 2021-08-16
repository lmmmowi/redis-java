package com.lmmmowi.redis.util;

import cn.hutool.core.io.NioUtil;

public class IoUtils {

    private IoUtils() {
    }

    public static void close(AutoCloseable closeable) {
        NioUtil.close(closeable);
    }
}
