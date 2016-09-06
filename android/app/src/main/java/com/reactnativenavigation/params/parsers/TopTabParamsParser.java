package com.reactnativenavigation.params.parsers;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.reactnativenavigation.params.NavigationParams;
import com.reactnativenavigation.params.TopTabParams;

import java.util.List;

public class TopTabParamsParser extends Parser {
    private static final String KEY_SCREEN_ID = "screenId";
    private static final String KEY_TITLE = "title";
    private static final String NAVIGATION_PARAMS = "navigationParams";

    @SuppressWarnings("ConstantConditions")
    public List<TopTabParams> parse(Bundle params) {
        return parseBundle(params, new ParseStrategy<TopTabParams>() {
            @Override
            public TopTabParams parse(Bundle topTabs) {
                return parseItem(topTabs);
            }
        });
    }

    @NonNull
    private static TopTabParams parseItem(Bundle params) {
        TopTabParams result = new TopTabParams();
        result.screenId = params.getString(KEY_SCREEN_ID);
        result.title = params.getString(KEY_TITLE);
        result.navigationParams = new NavigationParams(params.getBundle(NAVIGATION_PARAMS));
        result.leftButton = ButtonParser.parseLeftButton(params);
        result.rightButtons = ButtonParser.parseRightButton(params);
        result.fabParams = ButtonParser.parseFab(params);
        return result;
    }
}
