package com.reactnativenavigation.utils;

import java.util.Collection;

/**
 * Created by guyc on 11/04/16.
 */
public class CollectionUtils {

    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

}
