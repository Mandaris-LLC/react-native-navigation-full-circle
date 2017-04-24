package com.reactnativenavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.reactnativenavigation.viewcontrollers.ViewController;

public class NavigationActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
	private ViewController layout;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new FrameLayout(this));
		app().getInitializer().onActivityCreated(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		app().getInitializer().onActivityResumed(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		app().getInitializer().onActivityPaused(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		app().getInitializer().onActivityDestroyed(this);
	}


	public void setLayout(ViewController layout) {
		this.layout = layout;
		setContentView(layout.getView());
	}

	public ViewController getLayout() {
		return layout;
	}

	@Override
	public void invokeDefaultOnBackPressed() {
		onBackPressed();
	}

	@Override
	public void onBackPressed() {
		if (!layout.handleBack()) {
			super.onBackPressed();
		}
	}

	private NavigationApplication app() {
		return ((NavigationApplication) getApplication());
	}
}
