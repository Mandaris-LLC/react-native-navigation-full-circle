package com.reactnativenavigation.controllers;

import android.support.v7.app.AppCompatActivity;

import com.reactnativenavigation.layouts.ScreenStackContainer;
import com.reactnativenavigation.params.ScreenParams;

import java.util.Stack;

public class ModalController implements ScreenStackContainer, Modal.OnModalDismissedListener {
    private final AppCompatActivity activity;
    private Stack<Modal> stack = new Stack<>();

    public ModalController(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void showModal(ScreenParams screenParams) {
        Modal modal = new Modal(activity, this, screenParams);
        modal.show();
        stack.add(modal);
    }

    public void dismissTopModal() {
        if (isShowing()) {
            stack.pop().dismiss();
        }
    }

    public void dismissAllModals() {
        while (isShowing()) {
            dismissTopModal();
        }
    }

    public boolean isShowing() {
        return !stack.empty();
    }

    public void push(ScreenParams params) {
        stack.peek().push(params);
    }

    @Override
    public void pop(ScreenParams screenParams) {
        stack.peek().pop(screenParams);
    }

    @Override
    public void popToRoot(ScreenParams params) {
        stack.peek().popToRoot(params);
    }

    @Override
    public void newStack(ScreenParams params) {
        stack.peek().newStack(params);
    }

    @Override
    public void destroy() {
        for (Modal modal : stack) {
            modal.destroy();
            modal.dismiss();
        }
        stack.clear();
    }

    @Override
    public void onModalDismissed(Modal modal) {
        stack.remove(modal);
    }

}
