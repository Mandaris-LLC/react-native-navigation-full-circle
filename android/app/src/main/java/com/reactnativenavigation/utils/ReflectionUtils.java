package com.reactnativenavigation.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by guyc on 14/04/16.
 */
public class ReflectionUtils {

    public static boolean setField(Object obj, String name, Object value) {
        try {
            Field field = getField(obj.getClass(), name);
            if (field == null) {
                return false;
            }
            field.setAccessible(true);
            field.set(obj, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Field getField(Class clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException nsfe) {
            return getField(clazz.getSuperclass(), name);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the value of the field
     */
    public static Object getDeclaredField(Object obj, String fieldName) {
        try {
            Field f = getField(obj.getClass(), fieldName);
            if (f == null) {
                return null;
            }
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object invoke(Object object, String methodName) {
        try {
            Method method = object.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            return method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
