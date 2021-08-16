package com.lmmmowi.redis.util;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;

import java.util.Set;
import java.util.stream.Collectors;

public class ClassUtils {

    private ClassUtils() {
    }

    public static Class<?> getTypeArgument(Class<?> clazz, int index) {
        return ClassUtil.getTypeArgument(clazz, index);
    }

    public static <T> Set<Class<T>> scan(Package pkg, Class<T> interfaceType) {
        return ClassUtil.scanPackage(pkg.getName())
                .stream()
                .filter(clz -> !clz.isInterface() && !ClassUtil.isAbstract(clz))
                .filter(interfaceType::isAssignableFrom)
                .map(o -> (Class<T>) o)
                .collect(Collectors.toSet());
    }

    public static <T> T newInstance(Class<T> clz) {
        return ReflectUtil.newInstance(clz);
    }
}
