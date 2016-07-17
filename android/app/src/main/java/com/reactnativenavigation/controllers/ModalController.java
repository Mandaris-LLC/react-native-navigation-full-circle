package com.reactnativenavigation.controllers;

import android.support.annotation.Nullable;

import com.reactnativenavigation.modal.RnnModal;

import java.util.Stack;

public class ModalController {

    private final Stack<RnnModal> modals;

    public ModalController() {
        modals = new Stack<>();
    }

    public void add(RnnModal modal) {
        modals.add(modal);
    }

    public boolean isModalDisplayed() {
        return !modals.isEmpty();
    }

    @Nullable
    public RnnModal get() {
        return isModalDisplayed() ? modals.peek() : null;
    }

    public void remove() {
        if (isModalDisplayed()) {
            modals.pop();
        }
    }

    public void dismissAllModals() {
        while (isModalDisplayed()) {
            dismissModal();
        }
    }

    public void dismissModal() {
        if (isModalDisplayed()) {
            modals.pop().dismiss();
        }
    }

    public void onDestroy() {

    }

    public boolean onBackPressed() {
        return false;
    }
}
