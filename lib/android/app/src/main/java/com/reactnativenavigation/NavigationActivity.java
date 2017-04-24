package com.reactnativenavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.reactnativenavigation.viewcontrollers.ViewController;

public class NavigationActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
	private ViewController viewController;

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


	public void setViewController(ViewController viewController) {
		this.viewController = viewController;
		setContentView(viewController.getView());
	}

	public ViewController getViewController() {
		return viewController;
	}

	@Override
	public void invokeDefaultOnBackPressed() {
		onBackPressed();
	}

	@Override
	public void onBackPressed() {
		if (!viewController.handleBack()) {
			super.onBackPressed();
		}
	}

	private NavigationApplication app() {
		return ((NavigationApplication) getApplication());
	}
}
