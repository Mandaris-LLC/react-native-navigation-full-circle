package com.reactnativenavigation.layout;

import android.content.Context;
import android.widget.FrameLayout;

public class Container extends FrameLayout {
    public Container(Context context, LayoutFactory.RootViewCreator rootViewCreator, String id, String name) {
        super(context);
        addView(rootViewCreator.createRootView(id, name));
    }
}
