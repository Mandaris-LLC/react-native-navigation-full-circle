package com.reactnativenavigation.views.collapsingToolbar;

import com.reactnativenavigation.screens.Screen;
import com.reactnativenavigation.utils.ViewUtils;
import com.reactnativenavigation.views.utils.ViewMeasurer;

public class CollapsingContentViewMeasurer extends ViewMeasurer {
    private int titleBarHeight;
    private int screenHeight;

    public CollapsingContentViewMeasurer(final CollapsingTopBar topBar, final Screen collapsingSingleScreen) {
        ViewUtils.runOnPreDraw(topBar, new Runnable() {
            @Override
            public void run() {
                titleBarHeight = topBar.getCollapsedHeight();
            }
        });

        ViewUtils.runOnPreDraw(collapsingSingleScreen, new Runnable() {
            @Override
            public void run() {
                screenHeight = collapsingSingleScreen.getMeasuredHeight();
            }
        });
    }

    @Override
    public int getMeasuredHeight(int heightMeasureSpec) {
        return screenHeight - titleBarHeight;
    }
}
