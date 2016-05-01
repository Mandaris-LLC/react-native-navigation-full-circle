package com.reactnativenavigation.utils;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

/**
 * Created by guyc on 23/04/16.
 */
public class ImageUtils {

    public static void tint(Drawable drawable, int tint) {
        drawable.setColorFilter(new PorterDuffColorFilter(tint, PorterDuff.Mode.SRC_IN));
    }

}
