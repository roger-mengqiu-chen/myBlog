package com.myblog.myblog;

import java.lang.reflect.Field;

public class TestUtil {

    public static void injectObjects(Object target, String fieldName, Object inject) throws NoSuchFieldException, IllegalAccessException {
        Field f = target.getClass().getDeclaredField(fieldName);
        boolean wasPrivate = f.isAccessible();
        f.setAccessible(true);
        f.set(target, inject);
        if (wasPrivate) {
            f.setAccessible(false);
        }
    }
}
