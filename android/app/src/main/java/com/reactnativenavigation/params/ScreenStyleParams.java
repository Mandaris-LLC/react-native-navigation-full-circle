package com.reactnativenavigation.params;

import android.support.annotation.ColorInt;

public class ScreenStyleParams {
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
    public Color navigationBarColor;
    public boolean titleBarHidden;
    public boolean topBarHidden;
    public boolean backButtonHidden;
    public boolean topTabsHidden;
    public boolean bottomTabsHidden;
    public boolean bottomTabsHiddenOnScroll;
    public boolean drawUnderTopBar;
}
