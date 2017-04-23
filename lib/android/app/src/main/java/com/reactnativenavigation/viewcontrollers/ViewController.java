package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;

public class ViewController {
	private final Activity activity;
	private final View view;
	private StackController stackController;

	public ViewController(Activity activity) {
		this.activity = activity;
		this.view = onCreateView();
	}

	protected View onCreateView() {
		return new View(getActivity());
	}

	public boolean handleBack() {
		return false;
	}

	public Activity getActivity() {
		return activity;
	}

	@Nullable
	public StackController getParentStackController() {
		return stackController;
	}

	void setParentStackController(final StackController stackController) {
		this.stackController = stackController;
	}

	public View getView() {
		return view;
	}
}
