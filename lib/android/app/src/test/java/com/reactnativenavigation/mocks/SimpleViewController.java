package com.reactnativenavigation.mocks;

import android.app.Activity;
import android.view.View;

import com.reactnativenavigation.viewcontrollers.ViewController;

public class SimpleViewController extends ViewController {
	public SimpleViewController(final Activity activity) {
		super(activity);
	}

	@Override
	protected View onCreateView() {
		return new View(getActivity());
	}
}
