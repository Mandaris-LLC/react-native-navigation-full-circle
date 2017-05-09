package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

import com.reactnativenavigation.utils.CompatUtils;

import java.util.Collection;
import java.util.Collections;

public class Navigator extends ParentController {

	private ViewController root;
	private boolean activityResumed = false;

	public Navigator(final Activity activity) {
		super(activity, "navigator" + CompatUtils.generateViewId());
	}

	@NonNull
	@Override
	protected View createView() {
		return new FrameLayout(getActivity());
	}

	@Override
	public Collection<ViewController> getChildControllers() {
		return Collections.singletonList(root);
	}

	/*
	 * Activity lifecycle
	 */

	public boolean isActivityResumed() {
		return activityResumed;
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
		//
	}

	/*
	 * Navigation methods
	 */

	public void setRoot(final ViewController viewController) {
		getView().removeAllViews();

		root = viewController;
		getView().addView(viewController.getView());
	}

	public void push(final String onId, final ViewController viewController) {
		ViewController found = root.findControllerById(onId);
		if (found == null) return;

		StackController parentStackController = found.getParentStackController();
		if (parentStackController == null) return;

		parentStackController.push(viewController);
	}
}
