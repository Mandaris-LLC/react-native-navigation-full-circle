package com.reactnativenavigation.views;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.reactnativenavigation.animation.VisibilityAnimator;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;

import java.util.List;

public class TitleBar extends Toolbar {

    private boolean hideOnScroll = false;
    private VisibilityAnimator visibilityAnimator;

    public TitleBar(Context context) {
        super(context);
    }

    public void setRightButtons(List<TitleBarButtonParams> rightButtons, String navigatorEventId) {
        Menu menu = getMenu();
        menu.clear();

        if (rightButtons == null) {
            return;
        }

        addButtonsToTitleBar(rightButtons, navigatorEventId, menu);
    }

    private void addButtonsToTitleBar(List<TitleBarButtonParams> rightButtons, String navigatorEventId, Menu menu) {
        for (int i = 0; i < rightButtons.size(); i++) {
            final TitleBarButton button = new TitleBarButton(menu, this, rightButtons.get(i), navigatorEventId);
            addButtonInReverseOrder(rightButtons, i, button);
        }
    }

    private void addButtonInReverseOrder(List<TitleBarButtonParams> rightButtons, int i, TitleBarButton button) {
        final int index = rightButtons.size() - i - 1;
        button.addToMenu(index);
    }

    public void setLeftButton(TitleBarLeftButtonParams leftButtonParams,
                               TitleBarBackButtonListener titleBarBackButtonListener, String navigatorEventId) {
        LeftButton leftButton = new LeftButton(getContext(), leftButtonParams, titleBarBackButtonListener, navigatorEventId);
        setNavigationOnClickListener(leftButton);
        setNavigationIcon(leftButton);
    }

    public void setHideOnScroll(boolean hideOnScroll) {
        this.hideOnScroll = hideOnScroll;
    }

    public void onScrollChanged(ScrollDirectionListener.Direction direction) {
        if (hideOnScroll) {
            if (visibilityAnimator == null) {
                createScrollAnimator();
            }
            visibilityAnimator.onScrollChanged(direction);
        }
    }

    private void createScrollAnimator() {
        visibilityAnimator = new VisibilityAnimator(this, VisibilityAnimator.HideDirection.Up, getHeight());
    }
}
