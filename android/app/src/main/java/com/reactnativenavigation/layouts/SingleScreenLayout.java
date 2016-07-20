package com.reactnativenavigation.layouts;

import android.content.Context;
import android.widget.FrameLayout;

import com.reactnativenavigation.params.ScreenParams;

public class SingleScreenLayout extends FrameLayout {

    private final ScreenParams screenParams;

    public SingleScreenLayout(Context context, ScreenParams screenParams) {
        super(context);
        this.screenParams = screenParams;
    }

    public void createLayout() {
        ScreenLayout screenLayout = new ScreenLayout(getContext(), screenParams);
        addView(screenLayout);
    }
}
