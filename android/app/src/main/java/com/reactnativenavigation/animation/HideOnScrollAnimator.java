package com.reactnativenavigation.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;

import com.reactnativenavigation.views.ScrollDirectionListener;

public class HideOnScrollAnimator {

    public enum HideDirection {
        Up, Down
    }

    private enum State {
        Hidden, AnimateHide, Shown, AnimateShow
    }

    private static final int DURATION = 300;
    private final View view;
    private final HideDirection hideDirection;
    private final int hiddenEndValue;
    private final int shownEndValue = 0;
    private State state = State.Shown;

    public HideOnScrollAnimator(View view, HideDirection hideDirection, int height) {
        this.view = view;
        this.hideDirection = hideDirection;
        this.hiddenEndValue = hideDirection == HideDirection.Up ? -height : height;
    }

    public void onScrollChanged(ScrollDirectionListener.Direction scrollDirection) {
        if (hideDirection == HideDirection.Down) {
            handleDownHidingViews(scrollDirection); //TODO better name
        } else {
            handleUpHidingViews(scrollDirection);
        }
    }

    public void show() {
        ObjectAnimator animator = createAnimator(true);
        animator.start();
    }

    public void hide() {
        ObjectAnimator animator = createAnimator(false);
        animator.start();
    }

    private void handleUpHidingViews(ScrollDirectionListener.Direction scrollDirection) {
        if (scrollUp(scrollDirection) && !hiding()) {
            hide();
        } else if (scrollDown(scrollDirection) && !showing()) {
            show();
        }
    }

    private void handleDownHidingViews(ScrollDirectionListener.Direction scrollDirection) {
        if (scrollDown(scrollDirection) && !hiding()) {
            hide();
        } else if (scrollUp(scrollDirection) && !showing()) {
            show();
        }
    }

    private boolean scrollUp(ScrollDirectionListener.Direction scrollDirection) {
        return scrollDirection == ScrollDirectionListener.Direction.Up;
    }

    private boolean scrollDown(ScrollDirectionListener.Direction scrollDirection) {
        return scrollDirection == ScrollDirectionListener.Direction.Down;
    }

    private boolean showing() {
        return state == State.Shown || state == State.AnimateShow;
    }

    private boolean hiding() {
        return state == State.Hidden || state == State.AnimateHide;
    }

    private ObjectAnimator createAnimator(final boolean show) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, show ? shownEndValue : hiddenEndValue);
        animator.setDuration(DURATION);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                state = show ? State.AnimateShow : State.AnimateHide;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                state = show ? State.Shown : State.Hidden;
            }
        });
        return animator;
    }

}
