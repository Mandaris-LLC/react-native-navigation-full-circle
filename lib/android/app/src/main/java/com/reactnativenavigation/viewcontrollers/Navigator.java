package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

public class Navigator extends ViewController {
	private boolean activityResumed = false;

	public Navigator(final Activity activity) {
		super(activity);
	}

	@NonNull
	@Override
	protected View createView() {
		return new FrameLayout(getActivity());
	}

	@NonNull
	@Override
	public FrameLayout getView() {
		return (FrameLayout) super.getView();
	}

	public boolean isActivityResumed() {
		return activityResumed;
	}

	public void setRoot(final ViewController viewController) {
		getView().removeAllViews();
		getView().addView(viewController.getView());
	}

	public void onActivityCreated() {
		getActivity().setContentView(getView());
	}

	public void onActivityResumed() {
		activityResumed = true;
	}

	public void onActivityPaused() {
		activityResumed = false;
	}

	public void onActivityDestroyed() {
	}
}
