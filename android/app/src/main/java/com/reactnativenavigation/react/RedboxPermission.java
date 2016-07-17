package com.reactnativenavigation.react;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.common.ReactConstants;
import com.reactnativenavigation.BuildConfig;

public class RedboxPermission {

    public static void permissionToShowRedboxIfNeeded(Context context) {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(context)) {
            Intent serviceIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            context.startActivity(serviceIntent);
            String msg = "Overlay permissions needs to be granted in order for react native apps to run in dev mode";
            Log.w(ReactConstants.TAG, msg);
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }
}
