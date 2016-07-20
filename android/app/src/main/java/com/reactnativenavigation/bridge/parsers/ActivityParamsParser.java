package com.reactnativenavigation.bridge.parsers;

import android.os.Bundle;

import com.reactnativenavigation.controllers.ActivityParams;

public class ActivityParamsParser {
    public ActivityParams parse(Bundle params) {
        ActivityParams result = new ActivityParams();
        result.screenParams = new ScreenParamsParser().parse(params);
        return result;
    }
}
