package com.reactnativenavigation.mocks;

import android.app.Activity;
import android.view.View;

import com.reactnativenavigation.viewcontrollers.ViewController;

public class SimpleViewController extends ViewController {
	private final String name;

	public SimpleViewController(final Activity activity, String name) {
		super(activity);
		this.name = name;
	}

	@Override
	protected View createView() {
		return new View(getActivity());
	}

	@Override
	public String toString() {
		return "SimpleViewController " + name;
	}
}
