package com.reactnativenavigation.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.reactnativenavigation.params.TitleBarButtonParams;

// TODO: replace with vector menu drawable
public class LeftButton extends MaterialMenuDrawable implements View.OnClickListener {

    private static int getColor(TitleBarButtonParams params) {
        return params != null && params.color.hasColor() ?
                params.color.getColor() :
                Color.BLACK;
    }

    private TitleBarButtonParams params;
    private final TitleBarBackButtonListener titleBarBackButtonListener;
    private final String navigatorEventId;

    public LeftButton(Context context,
                      TitleBarButtonParams params,
                      TitleBarBackButtonListener titleBarBackButtonListener,
                      String navigatorEventId) {
        super(context, getColor(params), Stroke.THIN);
        this.params = params;
        this.titleBarBackButtonListener = titleBarBackButtonListener;
        this.navigatorEventId = navigatorEventId;
        setInitialState();
    }

    private void setInitialState() {
        if (params != null) {
            setIconState(getIconStateFromId(params.eventId));
        } else {
            setVisible(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (isBackButton()) {
            titleBarBackButtonListener.onTitleBarBackPress();
        }
    }

    // TODO: move to parser
    public IconState getIconStateFromId(String id) {
        switch (id) {
            case "back":
                return IconState.ARROW;
            case "cancel":
                return IconState.X;
            case "accept":
                return IconState.CHECK;
            case "sideMenu":
                return IconState.BURGER;
            default:
                throw new RuntimeException("Unsupported button type " + id);
        }
    }

    private boolean isBackButton() {
        return getIconState() == IconState.ARROW;
    }
}
