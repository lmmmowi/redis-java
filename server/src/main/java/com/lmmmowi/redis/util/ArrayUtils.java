package com.lmmmowi.redis.util;

import cn.hutool.core.util.ArrayUtil;

public class ArrayUtils {

    private ArrayUtils() {
    }

    public static <T> boolean isEmpty(T[] array) {
        return ArrayUtil.isEmpty(array);
    }
}
