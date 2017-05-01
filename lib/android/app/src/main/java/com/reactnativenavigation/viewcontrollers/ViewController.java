package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public abstract class ViewController {
	private View view;
	private final Activity activity;
	private StackController stackController;
	private String id;

	public ViewController(Activity activity, String id) {
		this.activity = activity;
		this.id = id;
	}

	@NonNull
	protected abstract View createView();

	public boolean handleBack() {
		return false;
	}

	public Activity getActivity() {
		return activity;
	}

	@Nullable
	public StackController getStackController() {
		return stackController;
	}

	void setStackController(final StackController stackController) {
		this.stackController = stackController;
	}

	@NonNull
	public View getView() {
		if (view == null) {
			view = createView();
		}
		return view;
	}

	public String getId() {
		return id;
	}
}
