package com.reactnativenavigation.controllers;

public class ModalController {
    public boolean onBackPressed() {
        return false;
    }

    public void onDestroy() {

    }

//    private final Stack<RnnModal> modals = new Stack<>();
//
//    public ModalController() {
//    }
//
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
