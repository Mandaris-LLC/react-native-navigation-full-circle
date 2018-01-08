package com.reactnativenavigation.mocks;

import android.app.Activity;

import com.reactnativenavigation.parse.NavigationOptions;
import com.reactnativenavigation.viewcontrollers.ComponentViewController;

public class SimpleComponentViewController extends ComponentViewController {
	public SimpleComponentViewController(final Activity activity, final String id) {
		super(activity, id, "theComponentName", new TestComponentViewCreator(), new NavigationOptions());
	}
}
