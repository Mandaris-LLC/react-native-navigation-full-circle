package com.reactnativenavigation.react;

import android.graphics.drawable.Drawable;

import com.reactnativenavigation.NavigationApplication;

public class ImageLoader {

    public static Drawable loadImage(String iconSource) {
        if (NavigationApplication.instance.isDebug()) {
            return JsDevImageLoader.loadIcon(iconSource);
        } else {
            return ResourceDrawableIdHelper.instance.getResourceDrawable(NavigationApplication.instance, iconSource);
        }
    }
}
