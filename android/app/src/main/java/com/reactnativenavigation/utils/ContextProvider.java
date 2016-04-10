package com.reactnativenavigation.utils;

import android.app.Activity;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 *
 * Created by guyc on 10/03/16.
 */
public class ContextProvider {
    private static WeakReference<Activity> sActivityWR;

    public static void setActivityContext(Activity activity) {
        if (sActivityWR == null) {
            sActivityWR = new WeakReference<>(activity);
        }
    }

    public static @Nullable Activity getActivityContext() {
        return sActivityWR != null ? sActivityWR.get() : null;
    }

    public static void clearActivityContext() {
        if (sActivityWR != null) {
            sActivityWR.clear();
        }
        sActivityWR = null;
    }
}
