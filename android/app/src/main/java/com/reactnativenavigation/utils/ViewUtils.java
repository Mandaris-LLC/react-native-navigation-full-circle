package com.reactnativenavigation.utils;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewTreeObserver;

import com.reactnativenavigation.NavigationApplication;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewUtils {
    private static final AtomicInteger viewId = new AtomicInteger();

    public static void runOnPreDraw(final View view, final Runnable task) {
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (!view.getViewTreeObserver().isAlive()) {
                    return true;
                }
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                task.run();
                return true;
            }
        });
    }

    public static void tintDrawable(Drawable drawable, int tint, boolean enabled) {
        drawable.setColorFilter(new PorterDuffColorFilter(enabled ? tint : Color.LTGRAY, PorterDuff.Mode.SRC_IN));
    }

    public static float convertDpToPixel(float dp) {
        float scale = NavigationApplication.instance.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static int generateViewId() {
        return viewId.incrementAndGet();
    }
}

