package com.reactnativenavigation.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;

import com.reactnativenavigation.parse.AnimationsOptions;

@SuppressWarnings("ResourceType")
public class NavigationAnimator extends BaseAnimator {

    public NavigationAnimator(Context context) {
        super(context);
    }

    public NavigationAnimator(Context context, AnimationsOptions options) {
        super(context);
        this.options = options;
    }

    public void push(final View view, AnimationListener animationListener) {
        view.setVisibility(View.INVISIBLE);
        AnimatorSet set = options.push.content.getAnimation(view, getDefaultPushAnimation(view));
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationListener.onAnimationEnd();
            }
        });
        set.start();
    }

    public void pop(View view, AnimationListener animationListener) {
        AnimatorSet set = options.pop.content.getAnimation(view, getDefaultPopAnimation(view));
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationListener.onAnimationEnd();
            }
        });
        set.start();
    }

    public void animateStartApp(View view, AnimationListener animationListener) {
        view.setVisibility(View.INVISIBLE);
        AnimatorSet set = options.startApp.getAnimation(view, null);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationListener != null) animationListener.onAnimationEnd();
            }
        });
        set.start();
    }

    public void setOptions(AnimationsOptions options) {
        this.options = options;
    }

    public void mergeOptions(AnimationsOptions options) {
        this.options.mergeWith(options);
    }
}
