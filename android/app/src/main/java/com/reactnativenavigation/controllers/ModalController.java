package com.reactnativenavigation.controllers;

import android.app.Activity;
import android.util.Log;

import com.reactnativenavigation.params.ScreenParams;

import java.util.Stack;

public class ModalController {
    private final Activity activity;
    private Stack<Modal> stack = new Stack<>();

    public ModalController(Activity activity) {
        this.activity = activity;
    }

    public void showModal(ScreenParams screenParams) {
        Modal modal = new Modal(activity, this, screenParams);
        modal.show();
        stack.add(modal);
    }

    public void dismissModal() {

    }

    public boolean onBackPressed() {
        Log.d("LOG", "ModalController.onBackPressed ");
        return false;
    }

    public void onDestroy() {

    }

    public boolean isShowing() {
        return !stack.empty();
    }

    public void modalDismissed(Modal modal) {
        stack.remove(modal);
    }

    public void push(ScreenParams params) {
        stack.peek().push(params);
    }
}
