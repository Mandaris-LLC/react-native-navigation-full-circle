package com.reactnativenavigation.controllers;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Window;

import com.reactnativenavigation.R;
import com.reactnativenavigation.layouts.Layout;
import com.reactnativenavigation.layouts.ScreenStackContainer;
import com.reactnativenavigation.layouts.SingleScreenLayout;
import com.reactnativenavigation.params.ScreenParams;

public class Modal extends Dialog implements DialogInterface.OnDismissListener, ScreenStackContainer {

    private final Activity activity;
    private final OnModalDismissedListener onModalDismissedListener;

    public interface OnModalDismissedListener {
        void onModalDismissed(Modal modal);
    }

    private final ScreenParams screenParams;
    private Layout layout;

    public Modal(Activity activity, OnModalDismissedListener onModalDismissedListener, ScreenParams screenParams) {
        super(activity, R.style.Modal);
        this.activity = activity;
        this.onModalDismissedListener = onModalDismissedListener;
        this.screenParams = screenParams;
        createContent();
    }

    public Activity getActivity() {
        return activity;
    }

    private void createContent() {
        setCancelable(true);
        setOnDismissListener(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        layout = new SingleScreenLayout(getActivity(), screenParams);
        setContentView(layout.asView());
    }

    @Override
    public void push(ScreenParams params) {
        layout.push(params);
    }

    @Override
    public void pop(ScreenParams screenParams) {
        layout.pop(screenParams);
    }

    @Override
    public void popToRoot(ScreenParams params) {
        layout.popToRoot(params);
    }

    @Override
    public void newStack(ScreenParams params) {
        layout.newStack(params);
    }

    @Override
    public void destroy() {
        layout.destroy();
    }

    @Override
    public void onBackPressed() {
        if (!layout.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        destroy();
        onModalDismissedListener.onModalDismissed(this);
    }
}
