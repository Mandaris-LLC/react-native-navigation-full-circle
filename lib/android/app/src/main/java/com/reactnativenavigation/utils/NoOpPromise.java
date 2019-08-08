package com.reactnativenavigation.utils;

import com.facebook.react.bridge.*;

import javax.annotation.Nullable;

public class NoOpPromise implements Promise {
    @Override
    public void resolve(@Nullable Object value) {

    }

    @Override
    public void reject(String code, String message) {

    }

    @Override
    public void reject(String code, Throwable e) {

    }

    @Override
    public void reject(String code, String message, Throwable e) {

    }

    @Deprecated
    @Override
    public void reject(String message) {

    }

    @Override
    public void reject(Throwable reason) {

    }

    /*Extra Methods*/

    @Override
    public void reject(String code, String message, Throwable e, WritableMap a) {

    }

   @Override
    public void reject(Throwable e, WritableMap a) {

    }

   @Override
    public void reject(String code, String message, WritableMap a) {

    }

   @Override
    public void reject(String code, Throwable e, WritableMap a) {

    }

   @Override
    public void reject(String code, WritableMap a) {

    }
}
