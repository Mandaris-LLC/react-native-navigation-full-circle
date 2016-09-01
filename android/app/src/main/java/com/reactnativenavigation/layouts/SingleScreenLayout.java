package com.reactnativenavigation.layouts;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.SideMenuParams;
import com.reactnativenavigation.params.SnackbarParams;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;
import com.reactnativenavigation.screens.ScreenStack;
import com.reactnativenavigation.views.LeftButtonOnClickListener;
import com.reactnativenavigation.views.SideMenu;
import com.reactnativenavigation.views.SnackbarContainer;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class SingleScreenLayout extends RelativeLayout implements Layout {

    private final AppCompatActivity activity;
    protected final ScreenParams screenParams;
    private final SideMenuParams sideMenuParams;
    protected ScreenStack stack;
    private SnackbarContainer snackbarContainer;
    protected LeftButtonOnClickListener leftButtonOnClickListener;
    private @Nullable SideMenu sideMenu;

    public SingleScreenLayout(AppCompatActivity activity, @Nullable SideMenuParams sideMenuParams, ScreenParams screenParams) {
        super(activity);
        this.activity = activity;
        this.screenParams = screenParams;
        this.sideMenuParams = sideMenuParams;
        createLayout();
    }

    private void createLayout() {
        if (sideMenuParams == null) {
            createStack(getScreenStackParent());
        } else {
            sideMenu = createSideMenu();
            createStack(getScreenStackParent());
        }
        createSnackbarContainer();
    }

    private RelativeLayout getScreenStackParent() {
        return sideMenu == null ? this : sideMenu.getContentContainer();
    }

    private SideMenu createSideMenu() {
        SideMenu sideMenu = new SideMenu(getContext(), sideMenuParams);
        RelativeLayout.LayoutParams lp = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        addView(sideMenu, lp);
        return sideMenu;
    }

    private void createStack(RelativeLayout parent) {
        if (stack != null) {
            stack.destroy();
        }
        stack = new ScreenStack(activity, parent, screenParams.getNavigatorId(), this);
        LayoutParams lp = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        pushInitialScreen(lp);
    }

    protected void pushInitialScreen(LayoutParams lp) {
        stack.pushInitialScreen(screenParams, lp);
        stack.show();
    }

    private void createSnackbarContainer() {
        snackbarContainer = new SnackbarContainer(getContext());
        RelativeLayout.LayoutParams lp = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        lp.addRule(ALIGN_PARENT_BOTTOM);
        snackbarContainer.setLayoutParams(lp);
        getScreenStackParent().addView(snackbarContainer);
    }

    @Override
    public boolean onBackPressed() {
        if (stack.handleBackPressInJs()) {
            return true;
        }

        if (stack.canPop()) {
            stack.pop(true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void destroy() {
        stack.destroy();
        snackbarContainer.destroy();
        if (sideMenu != null) {
            sideMenu.destroy();
        }
    }

    @Override
    public void push(ScreenParams params) {
        LayoutParams lp = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        stack.push(params, lp);
    }

    @Override
    public void pop(ScreenParams params) {
        stack.pop(params.animateScreenTransitions);
    }

    @Override
    public void popToRoot(ScreenParams params) {
        stack.popToRoot(params.animateScreenTransitions);
    }

    @Override
    public void newStack(ScreenParams params) {
        RelativeLayout parent = sideMenu == null ? this : sideMenu.getContentContainer();
        createStack(parent);
    }

    @Override
    public void setTopBarVisible(String screenInstanceID, boolean visible, boolean animate) {
        stack.setScreenTopBarVisible(screenInstanceID, visible, animate);
    }

    @Override
    public void setTitleBarTitle(String screenInstanceId, String title) {
        stack.setScreenTitleBarTitle(screenInstanceId, title);
    }

    @Override
    public View asView() {
        return this;
    }

    @Override
    public void setTitleBarRightButtons(String screenInstanceId, String navigatorEventId,
                                        List<TitleBarButtonParams> titleBarRightButtons) {
        stack.setScreenTitleBarRightButtons(screenInstanceId, navigatorEventId, titleBarRightButtons);
    }

    @Override
    public void setTitleBarLeftButton(String screenInstanceId, String navigatorEventId, TitleBarLeftButtonParams titleBarLeftButtonParams) {
        stack.setScreenTitleBarLeftButton(screenInstanceId, navigatorEventId, titleBarLeftButtonParams);
    }

    @Override
    public void toggleSideMenuVisible(boolean animated) {
        if (sideMenu != null) {
            sideMenu.toggleVisible(animated);
        }
    }

    @Override
    public void setSideMenuVisible(boolean animated, boolean visible) {
        if (sideMenu != null) {
            sideMenu.setVisible(visible, animated);
        }
    }

    @Override
    public void showSnackbar(SnackbarParams params) {
        final String navigatorEventId = stack.peek().getNavigatorEventId();
        snackbarContainer.showSnackbar(navigatorEventId, params);
    }

    @Override
    public boolean onTitleBarBackButtonClick() {
        if (leftButtonOnClickListener != null) {
            return leftButtonOnClickListener.onTitleBarBackButtonClick();
        }

        return onBackPressed();
    }

    @Override
    public void onSideMenuButtonClick() {
        if (sideMenu != null) {
            sideMenu.openDrawer();
        }
    }
}
