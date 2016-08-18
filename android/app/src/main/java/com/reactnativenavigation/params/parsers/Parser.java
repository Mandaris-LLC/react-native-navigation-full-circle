package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

public class Parser {

    protected static boolean hasKey(Bundle bundle, String key) {
        return bundle.keySet().contains(key);
    }


    protected static void assertKeyExists(Bundle bundle, String key) {
        if (!hasKey(bundle, key)) {
            throw new KeyDoesNotExistsException(key);
        }
    }

    public static class KeyDoesNotExistsException extends RuntimeException {
        public KeyDoesNotExistsException(String key) {
            super(key);
        }
    }
}
