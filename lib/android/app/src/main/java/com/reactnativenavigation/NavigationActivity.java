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
		app().activityLifecycle.onActivityCreated(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		app().activityLifecycle.onActivityResumed(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		app().activityLifecycle.onActivityPaused(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		app().activityLifecycle.onActivityDestroyed(this);
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

	private NavigationApplication app() {
		return ((NavigationApplication) getApplication());
	}
}
