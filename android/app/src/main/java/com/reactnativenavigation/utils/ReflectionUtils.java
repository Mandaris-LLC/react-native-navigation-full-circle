package com.reactnativenavigation.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by guyc on 14/04/16.
 */
public class ReflectionUtils {

    public static boolean setField(Object obj, String name, Object value) {
        Field field;
        try {
            field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Object getDeclaredField(Object obj, String fieldName) {
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(obj); //IllegalAccessException
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
