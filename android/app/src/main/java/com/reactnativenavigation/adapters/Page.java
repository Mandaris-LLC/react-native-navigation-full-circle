package com.reactnativenavigation.adapters;

import com.facebook.react.bridge.ReadableMap;

import java.io.Serializable;

/**
 *
 * Created by guyc on 02/04/16.
 */
public class Page implements Serializable {
    private static final long serialVersionUID = -1033475305421107791L;
    private static final String KEY_TITLE = "title";
    private static final String KEY_SCREEN = "screen";
    private static final String KEY_LABEL = "label";

    /*package*/ String title;
    /*package*/ String screenId;

    public Page(ReadableMap page) {
        title = page.getString(KEY_TITLE);
        screenId = page.getString(KEY_SCREEN);
    }
}
