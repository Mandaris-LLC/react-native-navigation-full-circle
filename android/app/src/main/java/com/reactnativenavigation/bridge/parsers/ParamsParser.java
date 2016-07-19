package com.reactnativenavigation.bridge.parsers;

import com.facebook.react.bridge.ReadableMap;

public interface ParamsParser<T> {
    T parse(ReadableMap params);
}
