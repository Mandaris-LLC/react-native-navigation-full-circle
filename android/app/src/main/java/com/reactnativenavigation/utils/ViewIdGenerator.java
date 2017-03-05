package com.reactnativenavigation.utils;

import android.os.Build;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewIdGenerator {
	private static final AtomicInteger viewId = new AtomicInteger(1);

	public static int generate() {
		if (Build.VERSION.SDK_INT >= 17) {
			return View.generateViewId();
		} else {
			return compatGenerateViewId();
		}
	}

	private static int compatGenerateViewId() {
		while (true) {
			final int result = viewId.get();
			// aapt-generated IDs have the high byte nonzero; clamp to the range under that.
			int newValue = result + 1;
			if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
			if (viewId.compareAndSet(result, newValue)) {
				return result;
			}
		}
	}
}
