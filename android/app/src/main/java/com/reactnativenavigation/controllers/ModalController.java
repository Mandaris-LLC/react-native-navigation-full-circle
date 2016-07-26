package com.reactnativenavigation.controllers;

import android.app.Activity;

import com.reactnativenavigation.params.ScreenParams;

import java.util.Stack;

public class ModalController {
    private final Activity activity;
    private Stack<Modal> stack = new Stack<>();

    public ModalController(Activity activity) {
        this.activity = activity;
    }


    public void showModal(ScreenParams screenParams) {
        Modal modal = new Modal(activity, screenParams);
        modal.show();
        stack.add(modal);
    }

    public boolean onBackPressed() {
        return false;
    }

    public void onDestroy() {

    }
    //    public void add(RnnModal modal) {
//        modals.add(modal);
//    }
//
//    public boolean isModalDisplayed() {
//        return !modals.isEmpty();
//    }
//
//    @Nullable
//    public RnnModal get() {
//        return isModalDisplayed() ? modals.peek() : null;
//    }
//
//    public void remove() {
//        if (isModalDisplayed()) {
//            modals.pop();
//        }
//    }
//
//    public void dismissAllModals() {
//        while (isModalDisplayed()) {
//            dismissModal();
//        }
//    }
//
//    public void dismissModal() {
//        if (isModalDisplayed()) {
//            modals.pop().dismiss();
//        }
//    }
//
//    public void onDestroy() {
//
//    }
//
//    public boolean onBackPressed() {
//        return false;
//    }
}
