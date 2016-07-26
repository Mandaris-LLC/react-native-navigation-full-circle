package com.reactnativenavigation.controllers;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;

import com.reactnativenavigation.R;
import com.reactnativenavigation.params.ScreenParams;

public class Modal extends Dialog implements DialogInterface.OnDismissListener {
    private final ScreenParams screenParams;

    public Modal(Context context, ScreenParams screenParams) {
        super(context, R.style.Modal);
        this.screenParams = screenParams;
        setCancelable(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }
}
