package com.reactnativenavigation.layouts;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.params.TitleBarButtonParams;

import java.util.List;

public interface Layout {
    boolean onBackPressed();

    void onDestroy();

    void removeAllReactViews();

    void push(ScreenParams params);

    void pop(ScreenParams params);

    void popToRoot(ScreenParams params);

    void newStack(ScreenParams params);

    void setTopBarVisible(String screenInstanceId, boolean hidden, boolean animated);

    void setTitleBarTitle(String screenInstanceId, String title);

    void setTitleBarButtons(String screenInstanceId, String navigatorEventId, List<TitleBarButtonParams> titleBarButtons);
}
