package com.reactnativenavigation.params.parsers;

import android.graphics.Color;
import android.support.annotation.ColorInt;

//TODO move to JS
public class ColorParser {
    @ColorInt
    public static int parse(String str) {
        return Color.parseColor(str);
    }
}
