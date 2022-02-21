package com.bawnorton.hangingspiders.common.reflect;

import java.lang.reflect.Field;

public class Reflection {
    public static Object getField(Object accessed, String fieldName) throws NoSuchFieldException {
        Field field = getField(accessed.getClass(), fieldName);
        try {
            field.setAccessible(true);
            return field.get(accessed);
        } catch (IllegalAccessException ignored) {}
        return null;
    }

    public static void setField(Object accessed, String fieldName, Object value) throws NoSuchFieldException {
        Field field = getField(accessed.getClass(), fieldName);
        try {
            field.setAccessible(true);
            field.set(accessed, value);
        } catch (IllegalAccessException ignored) {}
    }

    private static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            clazz = clazz.getSuperclass();
            if (clazz == null) throw e;
            return getField(clazz, fieldName);
        }
    }
}
