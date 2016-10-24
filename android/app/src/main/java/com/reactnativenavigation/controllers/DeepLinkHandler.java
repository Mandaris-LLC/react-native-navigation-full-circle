package com.reactnativenavigation.controllers;

import android.content.Intent;
import android.net.Uri;

import static android.content.Intent.ACTION_VIEW;

public class DeepLinkHandler {
    private static Uri deepLink;

    static void saveDeepLinkData(Uri deepLink) {
        DeepLinkHandler.deepLink = deepLink;
    }

    static boolean hasDeepLinkData() {
        return deepLink != null;
    }

    static void setDeepLinkData(Intent intent) {
        if (deepLink != null) {
            intent.setData(deepLink);
            intent.setAction(ACTION_VIEW);
            clear();
        }
    }

    private static void clear() {
        deepLink = null;
    }
}
