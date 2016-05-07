package com.reactnativenavigation.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Window;

import com.reactnativenavigation.core.objects.Screen;

/**
 * Created by guyc on 07/05/16.
 */
public class StyleHelper {
    public static void setWindowStyle(Window window, Context context, Screen screen) {
        if (SdkSupports.lollipop()) {
            final int black = ContextCompat.getColor(context, android.R.color.black);
            if (screen.statusBarColor != null) {
                window.setStatusBarColor(screen.statusBarColor);
            } else {
                window.setStatusBarColor(black);
            }

            if (screen.navigationBarColor != null) {
                window.setNavigationBarColor(screen.navigationBarColor);
            } else {
                window.setNavigationBarColor(black);
            }
        }

    }
}
