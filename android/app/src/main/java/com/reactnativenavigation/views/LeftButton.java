package com.reactnativenavigation.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.balysv.materialmenu.MaterialMenuDrawable;
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

    private void setInitialState() {
        if (params != null) {
            setIconState(params.iconState);
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

    private boolean isBackButton() {
        return getIconState() == IconState.ARROW;
    }
}
