package com.reactnativenavigation.views;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.reactnativenavigation.animation.OnScrollAnimator;
import com.reactnativenavigation.params.TitleBarButtonParams;

import java.util.List;

public class TitleBar extends Toolbar {

    private boolean hideOnScroll = false;
    private OnScrollAnimator onScrollAnimator;

    public TitleBar(Context context) {
        super(context);
    }

    public void setButtons(List<TitleBarButtonParams> buttons, String navigatorEventId) {
        Menu menu = getMenu();
        menu.clear();

        for (int i = 0; i < buttons.size(); i++) {
            final TitleBarButton button = new TitleBarButton(menu, this, buttons.get(i), navigatorEventId);
            // Add button in reverse order because in iOS index 0 starts at right
            final int index = buttons.size() - i - 1;
            button.addToMenu(index);
        }
    }

    public void setHideOnScroll(boolean hideOnScroll) {
        this.hideOnScroll = hideOnScroll;
    }

    public void onScrollChanged(ScrollDirectionListener.Direction direction) {
        if (hideOnScroll) {
            if (onScrollAnimator == null) {
                createScrollAnimator();
            }
            onScrollAnimator.onScrollChanged(direction);
        }
    }

    private void createScrollAnimator() {
        onScrollAnimator = new OnScrollAnimator(this, OnScrollAnimator.HideDirection.Up, getHeight());
    }
}
