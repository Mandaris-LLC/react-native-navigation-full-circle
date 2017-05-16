package com.reactnativenavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.reactnativenavigation.viewcontrollers.Navigator;

public class NavigationActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
	private Navigator navigator;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		navigator = new Navigator(this);
		navigator.onActivityCreated();
	}

	@Override
	protected void onResume() {
		super.onResume();
		navigator.onActivityResumed();
	}

	@Override
	protected void onPause() {
		super.onPause();
		navigator.onActivityPaused();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		navigator.onActivityDestroyed();
	}

	@Override
	public void invokeDefaultOnBackPressed() {
		onBackPressed();
	}

	@Override
	public void onBackPressed() {
		if (!navigator.handleBack()) {
			super.onBackPressed();
		}
	}

	
	@Override
	public boolean onKeyMultiple(final int keyCode, final int repeatCount, final KeyEvent event) {
		Toast.makeText(this, "onKeyMultiple", Toast.LENGTH_SHORT).show();
		if (keyCode == KeyEvent.KEYCODE_R && repeatCount == 2) {
			Toast.makeText(this, "RR!!!!", Toast.LENGTH_SHORT).show();
			return true;
		} else {
			return super.onKeyMultiple(keyCode, repeatCount, event);
		}
	}

	public Navigator getNavigator() {
		return navigator;
	}
}
