package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

import com.reactnativenavigation.utils.CompatUtils;

public class Navigator extends ViewController {
	private boolean activityResumed = false;
	private ViewController child;

	public Navigator(final Activity activity) {
		super(activity, "navigator" + CompatUtils.generateViewId());
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

	public void push(final ViewController onViewController, final ViewController viewController) {

	}

	public ViewController getViewController(final String id) {
		return null;
	}
}
