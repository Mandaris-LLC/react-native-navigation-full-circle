package com.reactnativenavigation.parse;


import com.reactnativenavigation.parse.params.Bool;
import com.reactnativenavigation.parse.params.NullBool;

import org.json.JSONObject;

public class NestedAnimationsOptions {
    public static NestedAnimationsOptions parse(JSONObject json) {
        NestedAnimationsOptions options = new NestedAnimationsOptions();
        if (json == null) return options;

        options.content = AnimationOptions.parse(json.optJSONObject("content"));
        options.bottomTabs = AnimationOptions.parse(json.optJSONObject("bottomTabs"));
        options.topBar = AnimationOptions.parse(json.optJSONObject("topBar"));

        return options;
    }

    public Bool enabled = new NullBool();
    public AnimationOptions content = new AnimationOptions();
    public AnimationOptions bottomTabs = new AnimationOptions();
    public AnimationOptions topBar = new AnimationOptions();

    void mergeWith(NestedAnimationsOptions other) {
        topBar.mergeWith(other.topBar);
        content.mergeWith(other.content);
        bottomTabs.mergeWith(other.bottomTabs);
        if (other.enabled.hasValue()) enabled = other.enabled;
    }

    void mergeWithDefault(NestedAnimationsOptions defaultOptions) {
        content.mergeWithDefault(defaultOptions.content);
        bottomTabs.mergeWithDefault(defaultOptions.bottomTabs);
        topBar.mergeWithDefault(defaultOptions.topBar);
        if (!enabled.hasValue()) enabled = defaultOptions.enabled;
    }

    public boolean hasValue() {
        return topBar.hasValue() || content.hasValue() || bottomTabs.hasValue();
    }
}
