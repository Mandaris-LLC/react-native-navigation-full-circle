package com.reactnativenavigation.views;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.ActionMenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.reactnativenavigation.utils.ImageUtils;
import com.reactnativenavigation.utils.ViewUtils;

import java.util.ArrayList;

public class TitleBarButton {
    public static class Params {
        public enum ShowAsAction {
            IfRoom(MenuItem.SHOW_AS_ACTION_IF_ROOM),
            Always(MenuItem.SHOW_AS_ACTION_ALWAYS),
            Never(MenuItem.SHOW_AS_ACTION_NEVER),
            WithText(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

            int action;

            ShowAsAction(int action) {
                this.action = action;
            }
        }

        // Todo: add id for click listener
        private final String label;
        private final Drawable icon;
        @ColorInt
        private final int color;
        private final ShowAsAction showAsAction;
        private final boolean enabled = true;

        public Params(String label, Drawable icon, int color, ShowAsAction showAsAction) {
            this.label = label;
            this.icon = icon;
            this.color = color;
            this.showAsAction = showAsAction;
        }
    }

    private final Menu menu;
    private final ActionMenuView parent;
    private Params buttonParams;

    public TitleBarButton(Menu menu, ActionMenuView parent, Params buttonParams) {
        this.menu = menu;
        this.parent = parent;
        this.buttonParams = buttonParams;
    }

    public MenuItem addToMenu(int index) {
        MenuItem item = menu.add(Menu.NONE, Menu.NONE, index, buttonParams.label);
        item.setShowAsAction(buttonParams.showAsAction.action);
        item.setEnabled(buttonParams.enabled);
        setIcon(item);
        setColor();
        return item;
    }


    private void setIcon(MenuItem item) {
        if (hasIcon()) {
            item.setIcon(buttonParams.icon);
        }
    }

    private void setColor() {
        if (!hasColor()) {
            return;
        }

        if (hasIcon()) {
            setIconColor();
        } else {
            setTextColor();
        }
    }

    private void setIconColor() {
        ImageUtils.tint(buttonParams.icon, buttonParams.color);
    }

    private void setTextColor() {
        ViewUtils.runOnPreDraw(parent, new Runnable() {
            @Override
            public void run() {
                ArrayList<View> outViews = findActualTextViewInMenuByLabel();
                setTextColorForFoundButtonViews(outViews);
            }
        });
    }

    @NonNull
    private ArrayList<View> findActualTextViewInMenuByLabel() {
        ArrayList<View> outViews = new ArrayList<>();
        parent.findViewsWithText(outViews, buttonParams.label, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        return outViews;
    }

    private void setTextColorForFoundButtonViews(ArrayList<View> outViews) {
        for (View button : outViews) {
            ((TextView) button).setTextColor(buttonParams.color);
        }
    }

    private boolean hasIcon() {
        return buttonParams.icon != null;
    }

    private boolean hasColor() {
        return buttonParams.color > 0;
    }

}
