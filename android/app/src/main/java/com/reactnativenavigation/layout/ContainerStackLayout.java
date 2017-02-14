package com.reactnativenavigation.layout;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

public class ContainerStackLayout extends FrameLayout implements StackLayout {

    private View removedView;

    public ContainerStackLayout(Context context) {
        super(context);
    }

    @Override
    public void push(View view) {
        addView(view);
        removedView = getChildAt(0);
        removeView(removedView);
    }

    @Override
    public void pop() {
        addView(removedView);
        removeView(getChildAt(0));
    }

    @Override
    public View asView() {
        return this;
    }
}
