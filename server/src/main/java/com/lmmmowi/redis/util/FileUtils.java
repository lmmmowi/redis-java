package com.lmmmowi.redis.util;

import cn.hutool.core.io.FileUtil;

import java.io.File;

public class FileUtils {

    private FileUtils() {
    }

    public static void mkParentDirs(File file) {
        FileUtil.mkParentDirs(file);
    }
}
