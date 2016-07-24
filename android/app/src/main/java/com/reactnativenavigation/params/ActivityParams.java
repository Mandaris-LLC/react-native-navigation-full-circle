package com.reactnativenavigation.params;

public class ActivityParams {
    public enum Type {
        SingleScreen, TabBased
    }

    public Type type;
    public ScreenParams screenParams;
}
