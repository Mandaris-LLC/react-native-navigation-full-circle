package com.reactnativenavigation.layouts;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.widget.RelativeLayout;

import com.reactnativenavigation.animation.OnScrollAnimator;
import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.ScreenStyleParams;
import com.reactnativenavigation.utils.SdkSupports;
import com.reactnativenavigation.views.ScrollDirectionListener;
import com.reactnativenavigation.views.TopBar;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public abstract class Screen extends RelativeLayout implements ScrollDirectionListener.OnScrollChanged {

    protected final ScreenParams screenParams;
    protected TopBar topBar;
    private OnScrollAnimator scrollAnimator;

    public Screen(Context context, ScreenParams screenParams) {
        super(context);
        this.screenParams = screenParams;

        createViews();
        setStyle(screenParams.styleParams);
    }

    private void createViews() {
        createTopBar();
        createTitleBar();
        createContent();
    }

    protected abstract void createContent();

    private void createTitleBar() {
        topBar.addTitleBarAndSetButtons(screenParams.buttons, screenParams.navigatorEventId);
        topBar.setTitle(screenParams.title);
    }

    private void createTopBar() {
        topBar = new TopBar(getContext());
        addView(topBar, new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
    }

    private void setStyle(ScreenStyleParams styleParams) {
        setStatusBarColor(styleParams.statusBarColor);
        setNavigationBarColor(styleParams.navigationBarColor);
        topBar.setStyle(styleParams);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(ScreenStyleParams.Color statusBarColor) {
        if (!SdkSupports.lollipop()) {
            return;
        }

        final Activity context = (Activity) getContext();
        final Window window = context.getWindow();
        if (statusBarColor.hasColor()) {
            window.setStatusBarColor(statusBarColor.getColor());
        } else {
            window.setStatusBarColor(Color.BLACK);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setNavigationBarColor(ScreenStyleParams.Color navigationBarColor) {
        if (!SdkSupports.lollipop()) {
            return;
        }

        final Activity context = (Activity) getContext();
        final Window window = context.getWindow();
        if (navigationBarColor.hasColor()) {
            window.setNavigationBarColor(navigationBarColor.getColor());
        } else {
            window.setNavigationBarColor(Color.BLACK);
        }
    }

    @Override
    public void onScrollChanged(ScrollDirectionListener.Direction direction) {
        if (scrollAnimator == null) {
            scrollAnimator = new OnScrollAnimator(topBar, OnScrollAnimator.HideDirection.Up, topBar.getHeight());
        }
        scrollAnimator.onScrollChanged(direction);
    }

    public abstract void ensureUnmountOnDetachedFromWindow();

    public abstract void preventUnmountOnDetachedFromWindow();
}
