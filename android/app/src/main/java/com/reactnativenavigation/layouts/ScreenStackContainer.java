package com.reactnativenavigation.layouts;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.views.TitleBarBackButtonListener;

public interface ScreenStackContainer extends TitleBarBackButtonListener {
    void push(ScreenParams screenParams);

    void pop(ScreenParams screenParams);

    void popToRoot(ScreenParams params);

    void newStack(ScreenParams params);

    void destroy();
}
