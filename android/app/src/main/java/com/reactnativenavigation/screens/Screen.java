package com.reactnativenavigation.screens;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.widget.RelativeLayout;

import com.reactnativenavigation.animation.VisibilityAnimator;
import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.ScreenStyleParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.utils.SdkSupports;
import com.reactnativenavigation.utils.ViewUtils;
import com.reactnativenavigation.views.ScrollDirectionListener;
import com.reactnativenavigation.views.TitleBarBackButtonListener;
import com.reactnativenavigation.views.TopBar;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public abstract class Screen extends RelativeLayout implements ScrollDirectionListener.OnScrollChanged {

    protected final ScreenParams screenParams;
    private final TitleBarBackButtonListener titleBarBackButtonListener;
    protected TopBar topBar;
    private VisibilityAnimator topBarVisibilityAnimator;

    public Screen(Context context, ScreenParams screenParams, TitleBarBackButtonListener titleBarBackButtonListener) {
        super(context);
        this.screenParams = screenParams;
        this.titleBarBackButtonListener = titleBarBackButtonListener;

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
        topBar.addTitleBarAndSetButtons(screenParams.rightButtons, screenParams.leftButton,
                titleBarBackButtonListener, screenParams.navigatorEventId);
        topBar.setTitle(screenParams.title);
    }

    private void createTopBar() {
        topBar = new TopBar(getContext());
        createTopBarVisibilityAnimator();
        addView(topBar, new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
    }

    private void createTopBarVisibilityAnimator() {
        ViewUtils.runOnPreDraw(topBar, new Runnable() {
            @Override
            public void run() {
                if (topBarVisibilityAnimator == null) {
                    topBarVisibilityAnimator = new VisibilityAnimator(topBar, VisibilityAnimator.HideDirection.Up, topBar.getHeight());
                }
            }
        });
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
        topBarVisibilityAnimator.onScrollChanged(direction);
    }

    public abstract void ensureUnmountOnDetachedFromWindow();

    public abstract void preventUnmountOnDetachedFromWindow();

    public String getScreenInstanceId() {
        return screenParams.screenInstanceId;
    }

    public void setTopBarVisible(boolean visible, boolean animate) {
        topBarVisibilityAnimator.setVisible(visible, animate);
    }

    public void setTitleBarTitle(String title) {
        topBar.setTitle(title);
    }

    public void setTitleBarButtons(String navigatorEventId, List<TitleBarButtonParams> titleBarButtons) {
        topBar.setTitleBarButtons(navigatorEventId, titleBarButtons);
    }
}
