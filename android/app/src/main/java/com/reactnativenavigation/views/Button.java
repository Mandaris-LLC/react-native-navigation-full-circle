package com.reactnativenavigation.views;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.reactnativenavigation.utils.ImageUtils;

import java.util.ArrayList;

public class Button {
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

    String label;
    Drawable icon;
    @ColorInt
    int color;
    ShowAsAction showAsAction;

    public MenuItem addToMenu(Activity context, Menu menu, int index) {
        MenuItem item = menu.add(Menu.NONE, Menu.NONE, index, label);
        setShowAsAction(item);
        setIcon(item);
        setColor(context, item);
        return item;
    }

    private void setShowAsAction(MenuItem item) {
        item.setShowAsAction(showAsAction.action);
    }

    private void setIcon(MenuItem item) {
        if (icon != null) {
            item.setIcon(icon);
        }
    }

    private void setColor(Activity context, MenuItem item) {
        if (!hasColor()) {
            return;
        }

        if (hasIcon()) {
            setIconColor();
        } else {
            setTextColor(context);
        }
    }

    private void setTextColor(Activity context) {
        final View decorView = context.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                decorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                ArrayList<View> outViews = findButtonTextView();
                setTextColorInternal(outViews);
            }

            @NonNull
            private ArrayList<View> findButtonTextView() {
                ArrayList<View> outViews = new ArrayList<>();
                decorView.findViewsWithText(outViews, label, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                return outViews;
            }

            private void setTextColorInternal(ArrayList<View> outViews) {
                for (View button : outViews) {
                    ((TextView) button).setTextColor(color);
                }
            }
        });
    }

    private void setIconColor() {
        ImageUtils.tint(icon, color);
    }

    private boolean hasIcon() {
        return icon != null;
    }

    private boolean hasColor() {
        return color > 0;
    }

}
