package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

public class Navigator extends ViewController {
	private boolean activityResumed = false;
	private ViewController pendingViewControllerStart;

	public Navigator(final Activity activity) {
		super(activity);
	}

	@Override
	protected View createView() {
		FrameLayout view = new FrameLayout(getActivity());
		return view;
	}

	public boolean isActivityResumed() {
		return activityResumed;
	}

	public void setRoot(final ViewController viewController) {
		getActivity().setContentView(viewController.getView());
		if (activityResumed) {
//			viewController.onStart();
		} else {
			pendingViewControllerStart = viewController;
		}
	}

	public void onActivityCreated() {
		getActivity().setContentView(getView());
	}

	public void onActivityResumed() {
		activityResumed = true;
		if (pendingViewControllerStart != null) {
//			pendingViewControllerStart.onStart();
			pendingViewControllerStart = null;
		}
	}

	public void onActivityPaused() {
		activityResumed = false;
	}

	public void onActivityDestroyed() {
	}
}
