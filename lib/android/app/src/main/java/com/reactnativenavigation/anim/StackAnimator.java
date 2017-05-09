package com.reactnativenavigation.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class StackAnimator {
	public void animatePush(final View target, final Runnable onComplete) {
		target.setAlpha(0);
		target.setTranslationY(0.08f * ((View) target.getParent()).getHeight());

		ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 1);
		alpha.setInterpolator(new DecelerateInterpolator());
		alpha.setDuration(200);

		ObjectAnimator translationY = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0);
		translationY.setInterpolator(new DecelerateInterpolator());
		translationY.setDuration(350);

		AnimatorSet set = new AnimatorSet();
		set.playTogether(translationY, alpha);
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(final Animator animation) {
				if (onComplete != null) onComplete.run();
			}
		});
		set.start();
	}
}
