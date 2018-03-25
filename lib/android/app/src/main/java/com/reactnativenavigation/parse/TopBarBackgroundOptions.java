package com.reactnativenavigation.parse;

import com.reactnativenavigation.parse.params.Color;
import com.reactnativenavigation.parse.params.NullColor;
import com.reactnativenavigation.parse.parsers.ColorParser;

import org.json.JSONObject;

public class TopBarBackgroundOptions {
    public static TopBarBackgroundOptions parse(JSONObject json) {
        TopBarBackgroundOptions options = new TopBarBackgroundOptions();
        if (json == null) {
            return options;
        }

        options.color = ColorParser.parse(json, "color");

        return options;
    }

    public Color color = new NullColor();

    void mergeWith(final TopBarBackgroundOptions other) {
        if (other.color.hasValue()) color = other.color;
    }

    void mergeWithDefault(TopBarBackgroundOptions defaultOptions) {
        if (!color.hasValue()) color = defaultOptions.color;
    }
}
