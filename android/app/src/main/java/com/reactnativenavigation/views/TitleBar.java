package com.reactnativenavigation.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.reactnativenavigation.animation.HideOnScrollAnimator;
import com.reactnativenavigation.params.TitleBarButtonParams;

import java.util.List;

public class TitleBar extends Toolbar {

    private boolean hideOnScroll = false;
    private HideOnScrollAnimator hideOnScrollAnimator;

    public TitleBar(Context context) {
        super(context);
    }

    public void setButtons(List<TitleBarButtonParams> buttons) {
        Menu menu = getMenu();
        menu.clear();

        for (int i = 0; i < buttons.size(); i++) {
            final TitleBarButton button = new TitleBarButton(menu, this, buttons.get(i));
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
            if (hideOnScrollAnimator == null) {
                createScrollAnimator();
            }
            hideOnScrollAnimator.onScrollChanged(direction);
        }
    }

    private void createScrollAnimator() {
        hideOnScrollAnimator = new HideOnScrollAnimator(this, HideOnScrollAnimator.HideDirection.Up, getHeight());
    }
}
