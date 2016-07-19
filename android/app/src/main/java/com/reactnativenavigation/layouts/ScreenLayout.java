package com.reactnativenavigation.layouts;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.view.Window;
import android.widget.LinearLayout;

import com.facebook.react.ReactInstanceManager;
import com.reactnativenavigation.controllers.ScreenParams;
import com.reactnativenavigation.controllers.ScreenStyleParams;
import com.reactnativenavigation.utils.SdkSupports;
import com.reactnativenavigation.views.ContentView;
import com.reactnativenavigation.views.ScrollDirectionListener;
import com.reactnativenavigation.views.TitleBarButton;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class ScreenLayout extends LinearLayout implements ScrollDirectionListener.OnScrollChanged {

    private final ReactInstanceManager reactInstanceManager;
    private final String moduleName;
    private final Bundle passProps;
    private final List<TitleBarButton.Params> buttons;
    private ContentView contentView;
    private TopBar topBar;

    public ScreenLayout(Context context, ReactInstanceManager reactInstanceManager, ScreenParams screenParams) {
        super(context);
        this.reactInstanceManager = reactInstanceManager;
        moduleName = screenParams.moduleName;
        passProps = screenParams.passProps;
        buttons = screenParams.buttons;
        setOrientation(VERTICAL);

        createViews();
        setStyle(screenParams.styleParams);
    }

    private void createViews() {
        addTopBar();
        topBar.addTitleBarAndSetButtons(buttons);
        addContentView();
    }

    private void addTopBar() {
        topBar = new TopBar(getContext());
        addView(topBar, new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
    }

    private void addContentView() {
        contentView = new ContentView(getContext(), reactInstanceManager, moduleName, passProps, this);
        addView(contentView, new LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    private void setStyle(ScreenStyleParams styleParams) {
        setStatusBarColor(styleParams.statusBarColor);
        setTopBarColor(styleParams.topBarColor);
        setNavigationBarColor(styleParams.navigationBarColor);
        topBar.setTitleBarVisibility(styleParams.titleBarHidden);
        topBar.setVisibility(styleParams.topBarHidden ? GONE : VISIBLE);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(@ColorInt int statusBarColor) {
        if (!SdkSupports.lollipop()) {
            return;
        }

        final Activity context = (Activity) getContext();
        final Window window = context.getWindow();
        if (statusBarColor > 0) {
            window.setStatusBarColor(statusBarColor);
        } else {
            window.setStatusBarColor(Color.BLACK);
        }
    }

    private void setTopBarColor(@ColorInt int topBarColor) {
        if (topBarColor > 0) {
            topBar.setBackgroundColor(topBarColor);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setNavigationBarColor(int navigationBarColor) {
        if (!SdkSupports.lollipop()) {
            return;
        }

        final Activity context = (Activity) getContext();
        final Window window = context.getWindow();
        if (navigationBarColor > 0) {
            window.setNavigationBarColor(navigationBarColor);
        } else {
            window.setNavigationBarColor(Color.BLACK);
        }
    }

    @Override
    public void onScrollChanged(ScrollDirectionListener.Direction direction) {

    }
}
