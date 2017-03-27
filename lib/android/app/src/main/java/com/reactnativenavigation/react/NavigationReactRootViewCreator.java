package com.reactnativenavigation.react;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.facebook.react.ReactRootView;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.layout.LayoutFactory.ReactRootViewCreator;

public class NavigationReactRootViewCreator implements ReactRootViewCreator {
	@Override
	public View create(Activity activity, final String id, final String name) {
		ReactRootView rootView = new ReactRootView(activity);
		Bundle opts = new Bundle();
		opts.putString("id", id);
		rootView.startReactApplication(NavigationApplication.instance.getReactNativeHost().getReactInstanceManager(), name, opts);
		return rootView;
	}
}
