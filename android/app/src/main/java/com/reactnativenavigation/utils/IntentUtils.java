package com.reactnativenavigation.utils;

import android.content.Intent;

import com.reactnativenavigation.NavigationApplication;

public class IntentUtils {
    public static Intent getLauncherIntent() {
        Intent intent = NavigationApplication.instance.getPackageManager().getLaunchIntentForPackage(NavigationApplication.instance.getPackageName());
        if (intent == null)
            intent = new Intent();
        intent.setPackage(null);
        intent.setFlags(0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return intent;
    }
}
