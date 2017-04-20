package com.reactnativenavigation.viewcontrollers;

import android.support.annotation.Nullable;
import android.view.View;

public class ViewController {
	private View view;
	private NavigationController navigationController;

	public View getView() {
		return view;
	}

	public void setView(final View view) {
		this.view = view;
	}

	@Nullable
	public NavigationController getNavigationController() {
		return navigationController;
	}

	void setNavigationController(final NavigationController navigationController) {
		this.navigationController = navigationController;
	}
}
