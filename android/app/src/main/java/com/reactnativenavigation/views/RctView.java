package com.reactnativenavigation.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.activities.BottomTabActivity;
import com.reactnativenavigation.core.objects.Screen;
import com.reactnativenavigation.utils.BridgeUtils;
import com.reactnativenavigation.utils.ReflectionUtils;

/**
 * Created by guyc on 10/03/16.
 */
public class RctView extends FrameLayout {
    private static final String TAG = "RctView";

    private BottomTabActivity mContext;
    private ReactRootView mReactRootView;
    private ScrollView mScrollView;
    private int mLastScrollY = -1;
    private final ViewTreeObserver.OnScrollChangedListener mScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
        @Override
        public void onScrollChanged() {
            if (!mScrollView.getViewTreeObserver().isAlive()) {
                return;
            }

            final int scrollY = mScrollView.getScrollY();
            if (scrollY != mLastScrollY && // Scroll position changed
                scrollY > 0 && // Ignore top overscroll
                scrollY < (mScrollView.getChildAt(0).getHeight() - mScrollView.getHeight())) { // Ignore bottom overscroll
                int direction = scrollY > mLastScrollY ?
                        BottomNavigation.SCROLL_DIRECTION_DOWN :
                        BottomNavigation.SCROLL_DIRECTION_UP;
                mLastScrollY = scrollY;
                mContext.onScrollChanged(direction);
            }
        }
    };
    private boolean mIsScrollEventListenerRegistered = false;

    private final View.OnAttachStateChangeListener mStateChangeListener =
            new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    Log.d(TAG, "onViewAttachedToWindow " + v.getClass());
                    mScrollView = getScrollView((ViewGroup) getParent());

                    if (mScrollView != null && !mIsScrollEventListenerRegistered) {
                        Log.i(TAG, "setting scroll listener");
                        addScrollListener();
                    } else {
                        Log.v(TAG, "doing nothing");
                    }
                }

                @Override
                public void onViewDetachedFromWindow(final View detachedView) {
                    Log.d(TAG, "onViewDetachedFromWindow. Removing scrollChangedListener");
                    removeScrollListener();

                    post(new Runnable() {
                        @Override
                        public void run() {
                            mScrollView = getScrollView((ViewGroup) getParent());
                            if (mScrollView != null && !mIsScrollEventListenerRegistered) {
                                Log.i(TAG, "run: setting scroll listener");
                                mIsScrollEventListenerRegistered = true;
                                addScrollListener();
                            } else {
                                Log.v(TAG, "doing nothing");
                            }
                        }
                    });
                }
            };

    /**
     * Interface used to run some code when the {@link ReactRootView} is visible.
     */
    public interface OnDisplayedListener {
        /**
         * This method will be invoked when the {@link ReactRootView} is visible.
         */
        void onDisplayed();
    }

    @SuppressWarnings("unchecked")
    public RctView(BaseReactActivity ctx, ReactInstanceManager rctInstanceManager, final Screen screen,
                   final OnDisplayedListener onDisplayedListener) {
        super(ctx);
        setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        final OnDisplayedListener onDisplayedListenerInternal = screen.bottomTabsHiddenOnScroll ?
            new OnDisplayedListener() {
                @Override
                public void onDisplayed() {
                    if (onDisplayedListener != null) {
                        onDisplayedListener.onDisplayed();
                    }

                    setupScrollViewWithBottomTabs();
                }
            } : onDisplayedListener;

        mReactRootView = new RnnReactRootView(ctx, onDisplayedListenerInternal);
        mReactRootView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        Bundle passProps = createPassProps(screen);
        String componentName = screen.screenId;
        mReactRootView.startReactApplication(rctInstanceManager, componentName, passProps);

        addView(mReactRootView);
    }

    private Bundle createPassProps(Screen screen) {
        Bundle passProps = new Bundle();
        passProps.putString(Screen.KEY_SCREEN_INSTANCE_ID, screen.screenInstanceId);
        passProps.putString(Screen.KEY_NAVIGATOR_ID, screen.navigatorId);
        passProps.putString(Screen.KEY_NAVIGATOR_EVENT_ID, screen.navigatorEventId);
        if (screen.passedProps != null) {
            BridgeUtils.addMapToBundle(screen.passedProps, passProps);
        }
        return passProps;
    }

    private void setupScrollViewWithBottomTabs() {
        mScrollView = getScrollView((ViewGroup) getParent());
        if (mScrollView != null) {
            mContext = (BottomTabActivity) getContext();
            attachStateChangeListener(mScrollView);
            addScrollListener();
        }
    }

    private ScrollView getScrollView(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);

            if (child instanceof ScrollView) {
                return (ScrollView) child;
            }

            if (child instanceof ViewGroup) {
                return getScrollView((ViewGroup) child);
            }
        }

        return null;
    }

    private void attachStateChangeListener(ScrollView scrollView) {
        scrollView.addOnAttachStateChangeListener(mStateChangeListener);
    }

    private void addScrollListener() {
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(mScrollChangedListener);
    }

    private void removeScrollListener() {
        mScrollView.getViewTreeObserver().removeOnScrollChangedListener(mScrollChangedListener);
    }

    /**
     * Must be called before view is removed from screen, but will be added again later. Setting mAttachScheduled
     * to true will prevent the component from getting unmounted once view is detached from screen.
     */
    public void onTemporallyRemovedFromScreen() {
        // Hack in order to prevent the react view from getting unmounted

        ReflectionUtils.setField(mReactRootView, "mAttachScheduled", true);
    }

    /**
     * Must be called before view is removed from screen inorder to ensure onDetachedFromScreen is properly
     * executed and componentWillUnmount is called
     */
    public void onRemoveFromScreen() {
        ReflectionUtils.setField(mReactRootView, "mAttachScheduled", false);
    }

    /**
     * Must be called when view is added again to screen inorder to ensure onDetachedFromScreen is properly
     * executed and componentWillUnmount is called
     */
    public void onReAddToScreen() {
        ReflectionUtils.setField(mReactRootView, "mAttachScheduled", false);
    }

    public void detachFromScreen() {
        ReflectionUtils.invoke(mReactRootView, "onDetachedFromWindow");
    }
}

