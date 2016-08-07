package com.reactnativenavigation.screens;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.reactnativenavigation.utils.ViewUtils;
import com.reactnativenavigation.views.BottomTabs;

public class ScreenAnimator {
    private static final String TAG = "ScreenAnimator";
    private BottomTabs bottomTabs;

    public ScreenAnimator() {

    }

    public ScreenAnimator(BottomTabs bottomTabs) {
        this.bottomTabs = bottomTabs;
    }

    public void show(Screen screenToShow, Runnable onScreenRemoved) {
        createPushAnimator(screenToShow, onScreenRemoved).start();
    }

    public void show(Screen screenToShow) {
        createPushAnimator(screenToShow).start();
    }

    public void hide(Screen screenToHide, Runnable onScreenRemoved) {
        createPopAnimator(screenToHide, onScreenRemoved).start();
    }

    private Animator createPushAnimator(final Screen screen) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(screen, View.ALPHA, 0.7f, 1);
        alpha.setStartDelay(100);
        alpha.setInterpolator(new LinearInterpolator());
        alpha.setDuration(150);

        final float delta = 0.08f * ViewUtils.getScreenHeight();
        ObjectAnimator translationY = ObjectAnimator.ofFloat(screen, View.TRANSLATION_Y, delta, 0);
        translationY.setInterpolator(new AccelerateInterpolator());
        translationY.setDuration(250);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(translationY, alpha);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                screen.setVisibility(View.VISIBLE);
            }
        });
        return set;
    }

    private Animator createPushAnimator(final Screen screenToShow, final Runnable onScreenRemoved) {
        final float translationYValue = 0.08f * ViewUtils.getScreenHeight();

        ObjectAnimator alpha = ObjectAnimator.ofFloat(screenToShow, View.ALPHA, 0, 1);
        alpha.setInterpolator(new DecelerateInterpolator());
        alpha.setDuration(200);

        ObjectAnimator translationY = ObjectAnimator.ofFloat(screenToShow, View.TRANSLATION_Y, translationYValue, 0);
        translationY.setInterpolator(new DecelerateInterpolator());
        translationY.setDuration(280);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(translationY, alpha);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                screenToShow.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onScreenRemoved.run();
            }
        });
        return set;
    }

    private Animator createPopAnimator(final Screen screenToHide, final Runnable onScreenRemoved) {
        final float translationYValue = 0.08f * ViewUtils.getScreenHeight();

        ObjectAnimator alpha = ObjectAnimator.ofFloat(screenToHide, View.ALPHA, 0);
        alpha.setInterpolator(new LinearInterpolator());
        alpha.setStartDelay(100);
        alpha.setDuration(150);

        ObjectAnimator translationY = ObjectAnimator.ofFloat(screenToHide, View.TRANSLATION_Y, translationYValue);
        translationY.setInterpolator(new AccelerateInterpolator());
        translationY.setDuration(250);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(translationY, alpha);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onScreenRemoved.run();
            }
        });
        return set;
    }
}
