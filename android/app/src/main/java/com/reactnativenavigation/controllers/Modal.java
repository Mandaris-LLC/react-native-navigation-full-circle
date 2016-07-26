package com.reactnativenavigation.controllers;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Window;

import com.reactnativenavigation.R;
import com.reactnativenavigation.layouts.Layout;
import com.reactnativenavigation.layouts.SingleScreenLayout;
import com.reactnativenavigation.params.ScreenParams;

public class Modal extends Dialog implements DialogInterface.OnDismissListener {
    private final ModalController modalController;
    private final ScreenParams screenParams;
    private Layout layout;

    public Modal(Activity activity, ModalController modalController, ScreenParams screenParams) {
        super(activity, R.style.Modal);
        this.modalController = modalController;
        this.screenParams = screenParams;
        createContent();
    }

    private void createContent() {
        setCancelable(true);
        setOnDismissListener(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        layout = new SingleScreenLayout(getContext(), screenParams);
        setContentView(layout.asView());
    }

    public void push(ScreenParams params) {
        layout.push(params);
    }

    @Override
    public void onBackPressed() {
        Log.d("LOG", "Modal.onBackPressed ");
        if (!layout.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        layout.removeAllReactViews();
        modalController.modalDismissed(this);
    }
}
