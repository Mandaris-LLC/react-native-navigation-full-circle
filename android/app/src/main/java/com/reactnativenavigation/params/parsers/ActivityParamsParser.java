package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.ActivityParams;

public class ActivityParamsParser extends Parser {
    public static ActivityParams parse(Bundle params) {
        ActivityParams result = new ActivityParams();
        if (hasKey(params, "screen")) {
            result.type = ActivityParams.Type.SingleScreen;
            result.screenParams = ScreenParamsParser.parse(params.getBundle("screen"));
        }

        if (hasKey(params, "tabs")) {
            result.type = ActivityParams.Type.TabBased;
            result.tabParams = ScreenParamsParser.parseTabs(params.getBundle("tabs"));
        }

        return result;
    }
}
