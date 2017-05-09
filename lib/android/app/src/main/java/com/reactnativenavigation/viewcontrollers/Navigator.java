package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
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
	protected ViewGroup createView() {
		return new FrameLayout(getActivity());
	}

	@Override
	public Collection<ViewController> getChildControllers() {
		return root == null ? Collections.<ViewController>emptyList() : Collections.singletonList(root);
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

	public void push(final String fromId, final ViewController viewController) {
		StackController parentStackController = findParentStackControllerForChildId(fromId);
		if (parentStackController != null) {
			parentStackController.push(viewController);
		}
	}

	public void pop(final String fromId) {
		StackController parentStackController = findParentStackControllerForChildId(fromId);
		if (parentStackController != null) {
			parentStackController.pop();
		}
	}

	public void popTo(final String fromId, final String toId) {
		StackController parentStackController = findParentStackControllerForChildId(fromId);
		if (parentStackController != null) {
			parentStackController.popTo(findControllerById(toId));
		}
	}
}
