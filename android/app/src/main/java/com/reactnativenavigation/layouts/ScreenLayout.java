package com.reactnativenavigation.layouts;

import android.content.Context;
import android.widget.LinearLayout;

public class ScreenLayout extends LinearLayout {

    public static class Params {

    }

    public ScreenLayout(Context context, Params params) {
        super(context);
        setOrientation(VERTICAL);
        addView();
    }
}
