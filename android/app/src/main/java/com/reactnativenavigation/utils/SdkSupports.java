package com.reactnativenavigation.utils;

import android.os.Build;

/**
 * Created by guyc on 23/04/16.
 */
public class SdkSupports {

    public static boolean lollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
