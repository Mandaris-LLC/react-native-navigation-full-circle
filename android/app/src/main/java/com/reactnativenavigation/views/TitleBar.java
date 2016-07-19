package com.reactnativenavigation.views;

import android.app.Activity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.reactnativenavigation.R;

import java.util.List;

public class TitleBar extends Toolbar {

    private Menu menu;
    private ActionMenuView actionMenuView;



    public TitleBar(Activity context) {
        super(context);
        createMenu();
    }

    private void createMenu() {
        MenuInflater menuInflater = ((Activity) getContext()).getMenuInflater();
        actionMenuView = new ActionMenuView(getContext());
        menu = actionMenuView.getMenu();
        menuInflater.inflate(R.menu.stub, menu);
        addView(actionMenuView);
        // TODO: Maybe setSupportActionBar
    }

    public void setButtons(List<Button> buttons) {
        removeView(actionMenuView);
        createMenu();

        Activity context = (Activity) getContext();
        for (int i = 0; i < buttons.size(); i++) {
            final Button button = buttons.get(i);
            final int index = buttons.size() - i - 1;
            button.addToMenu(context, menu, index);
        }
    }
}
