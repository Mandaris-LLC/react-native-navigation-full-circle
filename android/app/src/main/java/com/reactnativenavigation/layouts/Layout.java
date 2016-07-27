package com.reactnativenavigation.layouts;

import android.view.View;

import com.reactnativenavigation.params.TitleBarButtonParams;

import java.util.List;

public interface Layout extends ScreenStackContainer {
    View asView();

    boolean onBackPressed();

    void setTopBarVisible(String screenInstanceId, boolean hidden, boolean animated);

    void setTitleBarTitle(String screenInstanceId, String title);

    void setTitleBarButtons(String screenInstanceId, String navigatorEventId, List<TitleBarButtonParams> titleBarButtons);
}
