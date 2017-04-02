package com.reactnativenavigation.react;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.NavigationApplication;

public class ReactRootViewCreatorImpl implements ReactRootViewCreator {

	@Override
	public View create(Activity activity, final String id, final String name) {
		ReactRootView rootView = new ReactRootView(activity);
		Bundle opts = new Bundle();
		opts.putString("id", id);
		//TODO this seems like a hack
		ReactInstanceManager reactInstanceManager = ((NavigationApplication) activity.getApplication()).getReactNativeHost().getReactInstanceManager();
		rootView.startReactApplication(reactInstanceManager, name, opts);
		return rootView;
	}
}
