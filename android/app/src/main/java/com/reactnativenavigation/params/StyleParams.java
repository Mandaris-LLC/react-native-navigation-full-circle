package com.reactnativenavigation.params;

import android.support.annotation.ColorInt;

public class StyleParams {
    public static class Color {
        @ColorInt
        private int color = -1;

        public Color(int color) {
            this.color = color;
        }

        public boolean hasColor() {
            return color != -1;
        }

        public int getColor() {
            if (!hasColor()) {
                throw new RuntimeException("Color undefined");
            }
            return color;
        }
    }

    public Color statusBarColor;

    public Color topBarColor;
    public boolean topBarHidden;
    public boolean topTabsHidden;

    public boolean titleBarHidden;
    public Color titleBarTitleColor;
    public Color titleBarButtonColor;
    public boolean backButtonHidden;

    public boolean bottomTabsHidden;
    public boolean bottomTabsHiddenOnScroll;
    public Color bottomTabsColor;
    public Color selectedBottomTabsButtonColor;
    public Color bottomTabsButtonColor;

    public Color navigationBarColor;

    public boolean drawScreenBelowTopBar;
}
