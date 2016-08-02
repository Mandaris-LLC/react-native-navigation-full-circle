package com.reactnativenavigation.views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.react.ReactViewHacks;

public class ContentView extends ReactRootView {

    private final String screenId;
    private final Bundle passProps;
    private Bundle navigationParams;
    private ScrollViewDelegate scrollViewDelegate = new ScrollViewDelegate();

    public ContentView(Context context, final String screenId, Bundle passProps, Bundle navigationParams, final TopBar topBar) {
        super(context);
        this.screenId = screenId;
        this.passProps = passProps;
        this.navigationParams = navigationParams;
        attachToJS();

        scrollViewDelegate.setListener(new ScrollViewDelegate.OnScrollListener() {

            private ScrollDirection scrollDirectionComputer;


            @Override
            public void onScroll(ScrollView scrollView) {
                /**
                 * do our magic
                 */
                if (scrollDirectionComputer == null) {
                    scrollDirectionComputer = new ScrollDirection(scrollView);
                }

                int currentTopBarTranslation = (int) topBar.getTranslationY();
                int delta = scrollDirectionComputer.getScrollDelta();

                int minTranslation = -topBar.getTitleBar().getHeight();
                int maxTranslation = 0;

                ScrollDirection.Direction direction = scrollDirectionComputer.getScrollDirection();

                boolean reachedMinimum = direction == ScrollDirection.Direction.Up && currentTopBarTranslation <= minTranslation;
                boolean reachedMaximum = direction == ScrollDirection.Direction.Down && currentTopBarTranslation >= maxTranslation;

                if (direction == ScrollDirection.Direction.None || reachedMinimum || reachedMaximum) {
                    if (reachedMinimum) {
                        topBar.animate().translationY(minTranslation);
                    }
                    if (reachedMaximum) {
                        topBar.animate().translationY(maxTranslation);
                    }
                } else {

                    int target = currentTopBarTranslation - delta;
                    int bound = Math.min(Math.max(minTranslation, target), maxTranslation);

                    topBar.setTranslationY(bound);
                }

            }
        });
    }

    private void attachToJS() {
        ReactInstanceManager react = NavigationApplication.instance.getNavigationReactInstance().getReactInstanceManager();
        startReactApplication(react, screenId, mergePropsAndNavigationParams());
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        scrollViewDelegate.onViewAdded(child);
    }

    private Bundle mergePropsAndNavigationParams() {
        if (passProps != null) {
            navigationParams.putAll(passProps);
        }
        return navigationParams;
    }

    public void preventUnmountOnDetachedFromWindow() {
        ReactViewHacks.preventUnmountOnDetachedFromWindow(this);
    }

    public void ensureUnmountOnDetachedFromWindow() {
        ReactViewHacks.ensureUnmountOnDetachedFromWindow(this);
    }

    public void preventMountAfterReattachedToWindow() {
        ReactViewHacks.preventMountAfterReattachedToWindow(this);
    }
}
