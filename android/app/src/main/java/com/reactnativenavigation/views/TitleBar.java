package com.reactnativenavigation.views;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.reactnativenavigation.R;

import java.util.List;

public class TitleBar extends Toolbar {

    public static class Button {
        public enum ShowAsAction {
            IfRoom, Always, Never, WithText
        }

        String label;
        Drawable icon;
        @ColorInt int color;
        ShowAsAction showAsAction;
    }

    public TitleBar(Activity context) {
        super(context);
        createMenu();
    }

    private void createMenu() {
        MenuInflater menuInflater = ((Activity) getContext()).getMenuInflater();
        ActionMenuView actionMenuView = new ActionMenuView(getContext());
        Menu menu = actionMenuView.getMenu();
        menuInflater.inflate(R.menu.stub, menu);
        addView(actionMenuView);
        // TODO: Maybe setSupportActionBar
    }

    public void setButtons(List<Button> buttons) {
        Menu menu = new
    }
}
