package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.ActivityParams;

public class ActivityParamsParser {
    public static ActivityParams parse(Bundle params) {
        ActivityParams result = new ActivityParams();
        result.screenParams = ScreenParamsParser.parse(params.getBundle("screen"));
        return result;
    }
}
