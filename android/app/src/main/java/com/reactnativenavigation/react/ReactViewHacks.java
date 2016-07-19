package com.reactnativenavigation.react;

import com.facebook.react.ReactRootView;
import com.reactnativenavigation.utils.ReflectionUtils;

public class ReactViewHacks {

    public static void preventUnmountOnDetachedFromWindow(ReactRootView view) {
        ReflectionUtils.setField(view, "mAttachScheduled", true);
    }

    /**
     * Must be called before view is removed from screen inorder to ensure onDetachedFromScreen is properly
     * executed and componentWillUnmount is called
     */
    public static void ensureUnmountOnDetachedFromWindow(ReactRootView view) {
        ReflectionUtils.setField(view, "mAttachScheduled", false);
    }

}
