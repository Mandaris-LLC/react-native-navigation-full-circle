package com.reactnativenavigation.params.parsers;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.reactnativenavigation.params.TopTabParams;

import java.util.ArrayList;
import java.util.List;

public class TopTabParamsParser extends Parser {
    private static final String KEY_SCREEN_ID = "screenId";
    private static final String KEY_TITLE = "title";

    @SuppressWarnings("ConstantConditions")
    public static List<TopTabParams> parse(Bundle params) {
        List<TopTabParams> result = new ArrayList<>();
        for (String key : params.keySet()) {
            result.add(parseItem(params.getBundle(key)));
        }
        return result;
    }

    @NonNull
    private static TopTabParams parseItem(Bundle params) {
        TopTabParams result = new TopTabParams();
        result.screenId = params.getString(KEY_SCREEN_ID);
        result.title = params.getString(KEY_TITLE);
        return result;
    }
}
