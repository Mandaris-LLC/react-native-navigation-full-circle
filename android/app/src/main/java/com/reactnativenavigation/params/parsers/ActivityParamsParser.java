package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.ActivityParams;

public class ActivityParamsParser {
    public ActivityParams parse(Bundle params) {
        ActivityParams result = new ActivityParams();
        result.screenParams = new ScreenParamsParser().parse(params);
        return result;
    }
}
