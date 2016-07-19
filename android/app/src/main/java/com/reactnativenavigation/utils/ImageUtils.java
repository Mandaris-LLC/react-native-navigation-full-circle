package com.reactnativenavigation.utils;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

import com.reactnativenavigation.NavigationApplication;

public class ImageUtils {

    public static void tint(Drawable drawable, int tint) {
        drawable.setColorFilter(new PorterDuffColorFilter(tint, PorterDuff.Mode.SRC_IN));
    }

    public static float convertDpToPixel(float dp) {
        float scale = NavigationApplication.instance.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }
}
