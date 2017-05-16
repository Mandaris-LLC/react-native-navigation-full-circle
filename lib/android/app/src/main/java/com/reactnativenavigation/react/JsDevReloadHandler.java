package com.reactnativenavigation.react;

import android.view.KeyEvent;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;

public class JsDevReloadHandler {
	private final ReactInstanceManager reactInstanceManager;
	private long firstRTimestamp = 0;

	public JsDevReloadHandler(final ReactInstanceManager reactInstanceManager) {
		this.reactInstanceManager = reactInstanceManager;
		reactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
			@Override
			public void onReactContextInitialized(final ReactContext context) {
				//this is too late
			}
		});
	}

	public boolean onKeyUp(int keyCode) {
		if (!reactInstanceManager.getDevSupportManager().getDevSupportEnabled()) {
			return false;
		}

		if (keyCode == KeyEvent.KEYCODE_MENU) {
			reactInstanceManager.getDevSupportManager().showDevOptionsDialog();
			return true;
		}

		if (keyCode == KeyEvent.KEYCODE_R) {
			if (moreThan500MillisSinceLastR()) {
				reactInstanceManager.getDevSupportManager().handleReloadJS();
				return true;
			}
			firstRTimestamp = System.currentTimeMillis();
		}
		return false;
	}

	private boolean moreThan500MillisSinceLastR() {
		return firstRTimestamp != 0 && System.currentTimeMillis() - firstRTimestamp < 500;
	}
}
