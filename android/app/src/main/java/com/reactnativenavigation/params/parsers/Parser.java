package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

public class Parser {

    protected static boolean hasKey(Bundle bundle, String key) {
        return bundle.keySet().contains(key);
    }

}
