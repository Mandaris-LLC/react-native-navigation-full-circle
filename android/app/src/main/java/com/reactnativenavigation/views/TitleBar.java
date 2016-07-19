package com.reactnativenavigation.views;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.reactnativenavigation.R;
import com.reactnativenavigation.animation.HideOnScrollAnimator;

import java.util.List;

public class TitleBar extends Toolbar {

    private Menu menu;
    private ActionMenuView actionMenuView;
    private boolean hideOnScroll = false;
    private HideOnScrollAnimator hideOnScrollAnimator;

    public TitleBar(Context context) {
        super(context);
        createMenu();
    }

    public void setButtons(List<TitleBarButton.Params> buttons) {
        menu.clear();

        for (int i = 0; i < buttons.size(); i++) {
            final TitleBarButton button = new TitleBarButton(menu, actionMenuView, buttons.get(i));
            // Add button in reverse order because in iOS index 0 starts at right
            final int index = buttons.size() - i - 1;
            button.addToMenu(index);
        }
    }

    public void setupMenuButton() {
    }

    private void createMenu() {
        MenuInflater menuInflater = ((Activity) getContext()).getMenuInflater();
        actionMenuView = new ActionMenuView(getContext());
        menu = actionMenuView.getMenu();
        menuInflater.inflate(R.menu.stub, menu);
        addView(actionMenuView);
        // TODO: Maybe setSupportActionBar
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
