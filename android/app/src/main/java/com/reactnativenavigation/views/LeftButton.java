package com.reactnativenavigation.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.params.TitleBarButtonParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;

// TODO: replace with vector menu drawable
public class LeftButton extends MaterialMenuDrawable implements View.OnClickListener {

    private static int getColor(TitleBarButtonParams params) {
        return params != null && params.color.hasColor() ?
                params.color.getColor() :
                Color.BLACK;
    }

    private TitleBarLeftButtonParams params;
    private final TitleBarBackButtonListener titleBarBackButtonListener;
    private final String navigatorEventId;

    public LeftButton(Context context,
                      TitleBarLeftButtonParams params,
                      TitleBarBackButtonListener titleBarBackButtonListener,
                      String navigatorEventId) {
        super(context, getColor(params), Stroke.THIN);
        this.params = params;
        this.titleBarBackButtonListener = titleBarBackButtonListener;
        this.navigatorEventId = navigatorEventId;
        setInitialState();
    }

    public void setIconState(TitleBarLeftButtonParams params) {
        this.params = params;
        if (params.color.hasColor()) {
            setColor(params.color.getColor());
        }
        animateIconState(params.iconState);
    }

    @Override
    public void onClick(View v) {
        if (isBackButton()) {
            titleBarBackButtonListener.onTitleBarBackPress();
        } else {
            sendClickEvent();
        }
    }

    private void setInitialState() {
        if (params != null) {
            setIconState(params.iconState);
        } else {
            setVisible(false);
        }
    }

    private boolean isBackButton() {
        return getIconState() == IconState.ARROW;
    }

    private void sendClickEvent() {
        NavigationApplication.instance.sendEvent(params.eventId, navigatorEventId);
    }
}
