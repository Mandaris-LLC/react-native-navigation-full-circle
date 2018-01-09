package com.reactnativenavigation.mocks;

import android.app.Activity;

import com.reactnativenavigation.viewcontrollers.ComponentViewController;
import com.reactnativenavigation.viewcontrollers.ComponentViewController.ReactViewCreator;

public class TestComponentViewCreator implements ReactViewCreator {
	@Override
	public ComponentViewController.IReactView create(final Activity activity, final String componentId, final String componentName) {
		return new TestComponentLayout(activity);
	}
}
