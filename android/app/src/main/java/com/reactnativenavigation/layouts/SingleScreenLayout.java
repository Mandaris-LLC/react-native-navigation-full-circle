package com.reactnativenavigation.layouts;

import android.content.Context;
import android.widget.FrameLayout;

import com.reactnativenavigation.params.ScreenParams;

public class SingleScreenLayout extends FrameLayout implements Layout {

    private final ScreenParams screenParams;
    private ScreenLayout screenLayout;

    public SingleScreenLayout(Context context, ScreenParams screenParams) {
        super(context);
        this.screenParams = screenParams;
    }

    public void createLayout() {
        screenLayout = new ScreenLayout(getContext(), screenParams);
        addView(screenLayout);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void removeAllReactViews() {
        screenLayout.removeAllReactViews();
    }
}
