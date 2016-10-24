package com.reactnativenavigation.controllers;

import android.content.Intent;
import android.net.Uri;

import static android.content.Intent.ACTION_VIEW;

public class Linking {
    private static Uri initialUri;

    static void saveInitialUri(Uri initialUri) {
        Linking.initialUri = initialUri;
    }

    static void setInitialUri(Intent intent) {
        if (initialUri != null) {
            intent.setData(initialUri);
            intent.setAction(ACTION_VIEW);
            clear();
        }
    }

    private static void clear() {
        initialUri = null;
    }
}
