package com.reactnativenavigation.layouts;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.ScreenStyleParams;
import com.reactnativenavigation.utils.SdkSupports;
import com.reactnativenavigation.views.ContentView;
import com.reactnativenavigation.views.ScrollDirectionListener;
import com.reactnativenavigation.views.TopBar;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ScreenImpl extends LinearLayout implements Screen, ScrollDirectionListener.OnScrollChanged {

    private final ScreenParams screenParams;
    private ContentView contentView;
    private TopBar topBar;

    public ScreenImpl(Context context, ScreenParams screenParams) {
        super(context);
        this.screenParams = screenParams;
        setOrientation(VERTICAL);

        createViews();
        setStyle(screenParams.styleParams);
    }

    private void createViews() {
        addTopBar();
        addTitleBar();
        addContentView();
    }

    private void addTitleBar() {
        topBar.addTitleBarAndSetButtons(screenParams.buttons, screenParams.navigatorEventId);
        topBar.setTitle(screenParams.title);
    }

    private void addTopBar() {
        topBar = new TopBar(getContext());
        addView(topBar, new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
    }

    private void addContentView() {
        contentView = new ContentView(getContext(), screenParams, this);
        addView(contentView, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
        contentView.init();
    }

    private void setStyle(ScreenStyleParams styleParams) {
        setStatusBarColor(styleParams.statusBarColor);
        setTopBarColor(styleParams.topBarColor);
        setNavigationBarColor(styleParams.navigationBarColor);
        topBar.setTitleBarVisibility(styleParams.titleBarHidden);
        topBar.setVisibility(styleParams.topBarHidden ? GONE : VISIBLE);
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

    private void setTopBarColor(ScreenStyleParams.Color topBarColor) {
        if (topBarColor.hasColor()) {
            topBar.setBackgroundColor(topBarColor.getColor());
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
        // TODO handle if needed
    }

    @Override
    public View asView() {
        return this;
    }

    @Override
    public void ensureUnmountOnDetachedFromWindow() {
        contentView.ensureUnmountOnDetachedFromWindow();
    }
}
