package com.reactnativenavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;

public class NavigationActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
	private View contentView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NavigationApplication.instance.getConfig().activityLifecycleDelegate.onActivityCreated(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		NavigationApplication.instance.getConfig().activityLifecycleDelegate.onActivityResumed(this, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		NavigationApplication.instance.getConfig().activityLifecycleDelegate.onActivityPaused(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NavigationApplication.instance.getConfig().activityLifecycleDelegate.onActivityDestroyed(this);
	}

	@Override
	public void setContentView(View contentView) {
		super.setContentView(contentView);
		this.contentView = contentView;
	}

	@Nullable
	public View getContentView() {
		return contentView;
	}

	@Override
	public void invokeDefaultOnBackPressed() {
		onBackPressed();
	}
}
