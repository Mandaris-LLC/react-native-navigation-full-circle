package com.reactnativenavigation.layout;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

public class ContainerStackLayout extends FrameLayout {
    public ContainerStackLayout(Context context) {
        super(context);
    }

    public void push(View view) {
        addView(view);
        removeView(getChildAt(0));
    }
}
