package com.reactnativenavigation.views.collapsingToolbar;

import com.reactnativenavigation.views.utils.ViewMeasurer;

public class CollapsingContentViewMeasurer extends ViewMeasurer {
    private final float titleBarHeight;
    public CollapsingContentViewMeasurer(CollapsingTopBar topBar) {
        titleBarHeight = topBar.getTitleBarHeight();
    }

    @Override
    public int getMeasuredHeight(int heightMeasureSpec) {
        return (int) (super.getMeasuredHeight(heightMeasureSpec) + CollapsingToolBar.MAX_HEIGHT - titleBarHeight);
    }
}
