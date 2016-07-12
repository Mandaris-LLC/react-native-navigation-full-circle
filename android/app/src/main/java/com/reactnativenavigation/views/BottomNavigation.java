package com.reactnativenavigation.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

/**
 * Created by guyc on 10/07/16.
 */
public class BottomNavigation extends AHBottomNavigation {
    private static final String TAG = "BottomNavigation";
    public static final int SCROLL_DIRECTION_UP = 0;
    public static final int SCROLL_DIRECTION_DOWN = 1;

    private static final int STATE_HIDDEN = 0;
    private static final int STATE_ANIMATE_HIDE = 1;
    private static final int STATE_SHOWN = 2;
    private static final int STATE_ANIMATE_SHOW = 3;
    public static final int DURATION = 300;

    private int mState = STATE_SHOWN;
    private ObjectAnimator mHideAnimator;
    private ObjectAnimator mShowAnimator;


    public BottomNavigation(Context context) {
        super(context);
    }

    public BottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void toggleTabs(boolean hide, boolean animated) {
        if (hide) {
            hide(animated);
        } else {
            show(animated);
        }
    }

    private void hide(boolean animated) {
        if (animated) {
            hideAnimated();
        } else {
            setVisibility(View.GONE);
        }
    }

    private void hideAnimated() {
        if (mHideAnimator == null) {
            mHideAnimator = createHideAnimator();
        }

        mHideAnimator.start();
    }

    private ObjectAnimator createHideAnimator() {
        ObjectAnimator hideAnimator = ObjectAnimator.ofFloat(this, "translationY", getHeight());
        hideAnimator.setDuration(DURATION);
        hideAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mState = STATE_ANIMATE_HIDE;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mState = STATE_HIDDEN;
            }
        });
        hideAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        return hideAnimator;

    }

    private void show(boolean animated) {
        if (animated) {
            showAnimated();
        } else {
            setVisibility(View.VISIBLE);
        }
    }

    private void showAnimated() {
        if (mShowAnimator == null) {
            mShowAnimator = createShowAnimator();
        }

        mShowAnimator.start();
    }

    private ObjectAnimator createShowAnimator() {
        ObjectAnimator showAnimator = ObjectAnimator.ofFloat(this, "translationY", 0);
        showAnimator.setDuration(DURATION);
        showAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mState = STATE_ANIMATE_SHOW;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mState = STATE_SHOWN;
            }
        });
        showAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        return showAnimator;
    }


    public void onScroll(int direction) {
        Log.d(TAG, "onScroll() called with: " + "direction = [" + direction + "]");
        if (direction == SCROLL_DIRECTION_DOWN && mState == STATE_SHOWN) {
            hide(true);

        } else if (direction == SCROLL_DIRECTION_UP && mState == STATE_HIDDEN) {
            show(true);
        }
    }
}
