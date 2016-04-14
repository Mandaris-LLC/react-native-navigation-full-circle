package com.reactnativenavigation.utils;

import java.lang.reflect.Field;

/**
 * Created by guyc on 14/04/16.
 */
public class ReflectionUtils {

    public static boolean setBooleanField(Object obj, String name, Boolean value) {
        Field field;
        try {
            field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
            return true;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}
