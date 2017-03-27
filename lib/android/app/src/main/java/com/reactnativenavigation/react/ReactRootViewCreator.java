package com.reactnativenavigation.react;

import android.app.Activity;
import android.view.View;

public interface ReactRootViewCreator {
	View create(Activity activity, final String id, final String name);
}
