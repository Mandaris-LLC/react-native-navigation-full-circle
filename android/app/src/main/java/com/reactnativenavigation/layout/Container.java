package com.reactnativenavigation.layout;

import android.content.Context;
import android.widget.FrameLayout;

public class Container extends FrameLayout {
	public Container(Context context, LayoutFactory.ReactRootViewCreator reactRootViewCreator, String id, String name) {
		super(context);
		addView(reactRootViewCreator.create(id, name));
	}
}
