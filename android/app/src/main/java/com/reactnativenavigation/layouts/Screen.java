package com.reactnativenavigation.layouts;

import android.view.View;

public interface Screen {
    View asView();

    void preventUnmountOnDetachedFromWindow();

    void ensureUnmountOnDetachedFromWindow();
}
