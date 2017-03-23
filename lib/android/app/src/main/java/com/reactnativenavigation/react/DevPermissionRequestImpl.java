package com.reactnativenavigation.react;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.common.ReactConstants;
import com.reactnativenavigation.NavigationApplication;

public class DevPermissionRequestImpl implements DevPermissionRequest {

	public boolean shouldAskPermission() {
		return NavigationApplication.instance.isDebug() &&
				Build.VERSION.SDK_INT >= 23 &&
				!Settings.canDrawOverlays(NavigationApplication.instance);
	}

	@Override
	@TargetApi(23)
	public void askPermission() {
		if (shouldAskPermission()) {
			Intent serviceIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
			serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			NavigationApplication.instance.startActivity(serviceIntent);
			String msg = "Overlay permissions needs to be granted in order for react native apps to run in dev mode";
			Log.w(ReactConstants.TAG, "======================================\n\n");
			Log.w(ReactConstants.TAG, msg);
			Log.w(ReactConstants.TAG, "\n\n======================================");
			Toast.makeText(NavigationApplication.instance, msg, Toast.LENGTH_LONG).show();
		}
	}
}
