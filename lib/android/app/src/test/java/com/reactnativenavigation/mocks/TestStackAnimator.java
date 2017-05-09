package com.reactnativenavigation.mocks;

import android.view.View;

import com.reactnativenavigation.anim.StackAnimator;

public class TestStackAnimator extends StackAnimator {
	@Override
	public void animatePush(final View target, final Runnable onComplete) {
		if (onComplete != null) onComplete.run();
	}
}
