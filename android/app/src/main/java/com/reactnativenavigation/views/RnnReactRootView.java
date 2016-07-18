package com.reactnativenavigation.views;

import android.content.Context;
import android.view.ViewTreeObserver;

import com.facebook.react.ReactRootView;

/**
 * Created by guyc on 11/07/16.
 */
public class RnnReactRootView extends ReactRootView implements ViewTreeObserver.OnGlobalLayoutListener {
    private final RctView.OnDisplayedListener mOnDisplayedListener;

    public RnnReactRootView(Context context, RctView.OnDisplayedListener onDisplayedListener) {
        super(context);
        mOnDisplayedListener = onDisplayedListener;

        if (onDisplayedListener != null) {
            detectOnDisplay();
        }
    }

    private void detectOnDisplay() {
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if (getChildCount() >= 1) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
            mOnDisplayedListener.onDisplayed();
        }
    }
}
