package com.reactnativenavigation.utils;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.reactnativenavigation.parse.params.Button;

import java.util.ArrayList;

public class ButtonOptionsPresenter {
    private final Toolbar toolbar;
    private Button button;

    public ButtonOptionsPresenter(Toolbar toolbar, Button button) {
        this.toolbar = toolbar;
        this.button = button;
    }

    public void tint(Drawable drawable, int tint) {
        drawable.setColorFilter(new PorterDuffColorFilter(tint, PorterDuff.Mode.SRC_IN));
    }

    public void setTypeFace(Typeface typeface) {
        UiUtils.runOnPreDrawOnce(toolbar, () -> {
            ArrayList<View> buttons = findActualTextViewInMenu();
            for (View btn : buttons) {
                ((TextView) btn).setTypeface(typeface);
            }
        });
    }

    public void setTextColor() {
        UiUtils.runOnPreDrawOnce(toolbar, () -> {
            ArrayList<View> buttons = findActualTextViewInMenu();
            for (View btn : buttons) {
                if (button.enabled.isTrueOrUndefined() && button.color.hasValue()) {
                    setEnabledColor((TextView) btn);
                } else if (button.enabled.isFalse()) {
                    setDisabledColor((TextView) btn, button.disabledColor.get(Color.LTGRAY));
                }
            }
        });
    }

    private void setDisabledColor(TextView btn, int color) {
        btn.setTextColor(color);
    }

    private void setEnabledColor(TextView btn) {
        btn.setTextColor(button.color.get());
    }

    @NonNull
    private ArrayList<View> findActualTextViewInMenu() {
        ArrayList<View> outViews = new ArrayList<>();
        toolbar.findViewsWithText(outViews, button.title.get(), View.FIND_VIEWS_WITH_TEXT);
        return outViews;
    }

}
