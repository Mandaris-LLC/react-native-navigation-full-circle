package com.reactnativenavigation.views;

import android.os.Bundle;
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

    private BottomTabActivity context;
    private ReactRootView reactRootView;
    private ScrollView scrollView;
    private int lastScrollY = -1;
    private final ViewTreeObserver.OnScrollChangedListener scrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
        @Override
        public void onScrollChanged() {
            if (!scrollView.getViewTreeObserver().isAlive()) {
                return;
            }

            final int scrollY = scrollView.getScrollY();
            if (scrollY != lastScrollY && // Scroll position changed
                scrollY > 0 && // Ignore top overscroll
                scrollY < (scrollView.getChildAt(0).getHeight() - scrollView.getHeight())) { // Ignore bottom overscroll
                int direction = scrollY > lastScrollY ?
                        BottomNavigation.SCROLL_DIRECTION_DOWN :
                        BottomNavigation.SCROLL_DIRECTION_UP;
                lastScrollY = scrollY;
                context.onScrollChanged(direction);
            }
        }
    };
    private boolean isScrollEventListenerRegistered = false;

    private final View.OnAttachStateChangeListener stateChangeListener =
            new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    scrollView = getScrollView((ViewGroup) getParent());

                    if (scrollView != null && !isScrollEventListenerRegistered) {
                        addScrollListener();
                    }
                }

                @Override
                public void onViewDetachedFromWindow(final View detachedView) {
                    removeScrollListener();

                    post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView = getScrollView((ViewGroup) getParent());
                            if (scrollView != null && !isScrollEventListenerRegistered) {
                                isScrollEventListenerRegistered = true;
                                addScrollListener();
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

        reactRootView = new RnnReactRootView(ctx, onDisplayedListenerInternal);
        reactRootView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        Bundle passProps = createPassProps(screen);
        String componentName = screen.screenId;
        reactRootView.startReactApplication(rctInstanceManager, componentName, passProps);

        addView(reactRootView);
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
        scrollView = getScrollView((ViewGroup) getParent());
        if (scrollView != null) {
            context = (BottomTabActivity) getContext();
            attachStateChangeListener(scrollView);
            addScrollListener();
        }
    }


    private void attachStateChangeListener(ScrollView scrollView) {
        scrollView.addOnAttachStateChangeListener(stateChangeListener);
    }

    private void addScrollListener() {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(scrollChangedListener);
    }

    private void removeScrollListener() {
        scrollView.getViewTreeObserver().removeOnScrollChangedListener(scrollChangedListener);
    }

    /**
     * Must be called before view is removed from screen, but will be added again later. Setting mAttachScheduled
     * to true will prevent the component from getting unmounted once view is detached from screen.
     */
    public void onTemporallyRemovedFromScreen() {
        // Hack in order to prevent the react view from getting unmounted

        ReflectionUtils.setField(reactRootView, "mAttachScheduled", true);
    }

    /**
     * Must be called before view is removed from screen inorder to ensure onDetachedFromScreen is properly
     * executed and componentWillUnmount is called
     */
    public void onRemoveFromScreen() {
        ReflectionUtils.setField(reactRootView, "mAttachScheduled", false);
    }

    /**
     * Must be called when view is added again to screen inorder to ensure onDetachedFromScreen is properly
     * executed and componentWillUnmount is called
     */
    public void onReAddToScreen() {
        ReflectionUtils.setField(reactRootView, "mAttachScheduled", false);
    }

    public void detachFromScreen() {
        ReflectionUtils.invoke(reactRootView, "onDetachedFromWindow");
    }
}

