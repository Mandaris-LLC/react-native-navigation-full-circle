package com.reactnativenavigation.views;

import android.content.Context;
import android.util.AttributeSet;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

/**
 * Created by guyc on 10/07/16.
 */
public class BottomNavigation extends AHBottomNavigation {

    public BottomNavigation(Context context) {
        super(context);
    }

    public BottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Synchronize the BottomNavigation with a {@link android.widget.ScrollView}. When
     */
    public void setupWithScrollView() {

    }


}
