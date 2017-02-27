package com.reactnativenavigation.views;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.reactnativenavigation.R;

public class NavigationSplashView extends FrameLayout {
    public NavigationSplashView(Context context) {
        super(context);
        ImageView image = new ImageView(context);
        image.setImageResource(R.drawable.logo);
        image.setContentDescription("NavigationSplashView");
        addView(image);
    }
}
