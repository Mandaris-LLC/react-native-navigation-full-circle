package com.reactnativenavigation.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

/**
 * Created by guyc on 10/07/16.
 */
public class BottomNavigation extends AHBottomNavigation {

    public static final int SCROLL_DIRECTION_UP = 0;
    public static final int SCROLL_DIRECTION_DOWN = 1;

    public BottomNavigation(Context context) {
        super(context);
    }

    public BottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // TODO: support animated = false -guyca
    public void toggleTabs(boolean hide, boolean animated) {
        if (hide) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // mBottomNavigation.hideBottomNavigation(animated);
        setVisibility(View.GONE);
    }

    private void show() {
        setVisibility(View.VISIBLE);
        // mBottomNavigation.restoreBottomNavigation(animated);
    }

    public void onScroll(int direction) {
        if (direction == SCROLL_DIRECTION_DOWN) {
            hideBottomNavigation(true);
        } else if (direction == SCROLL_DIRECTION_UP) {
            restoreBottomNavigation(true);
        }
    }
}
