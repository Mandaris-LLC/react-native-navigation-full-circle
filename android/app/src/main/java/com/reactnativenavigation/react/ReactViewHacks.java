package com.reactnativenavigation.react;

import com.facebook.react.ReactRootView;
import com.reactnativenavigation.utils.ReflectionUtils;

public class ReactViewHacks {

    public static void preventUnmountOnDetachedFromWindow(ReactRootView view) {
        ReflectionUtils.setField(view, "mAttachScheduled", true);
    }

    /**
     * Side effect: prevents JS components constructor from being called
     */
    public static void ensureUnmountOnDetachedFromWindow(ReactRootView view) {
        ReflectionUtils.setField(view, "mAttachScheduled", false);
    }

    /**
     * Side effect: ensures unmount will be called
     */
    public static void preventMountAfterReattachedToWindow(ReactRootView view) {
        ReflectionUtils.setField(view, "mAttachScheduled", false);
    }

}
