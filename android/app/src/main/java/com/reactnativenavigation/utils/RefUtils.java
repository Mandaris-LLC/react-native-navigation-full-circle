package com.reactnativenavigation.utils;

import java.lang.ref.WeakReference;

/**
 * Created by guyc on 06/05/16.
 */
public class RefUtils {
    /**
     * Extract the object within a WeakReference object
     * @param wr the WeakReference to extract
     * @return the object within the WR or null when object not available.
     */
    public static <T> T get(WeakReference<T> wr) {
        if (wr != null) {
            return wr.get();
        }
        return null;
    }
}
