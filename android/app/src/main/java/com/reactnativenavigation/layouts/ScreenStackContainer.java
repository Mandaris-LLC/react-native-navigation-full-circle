package com.reactnativenavigation.layouts;

import com.reactnativenavigation.params.ScreenParams;

public interface ScreenStackContainer {
    void push(ScreenParams screenParams);

    void pop(ScreenParams screenParams);

    void popToRoot(ScreenParams params);

    void newStack(ScreenParams params);

    void destroy();
}
