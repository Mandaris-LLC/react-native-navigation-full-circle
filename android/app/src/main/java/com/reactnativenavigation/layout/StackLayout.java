package com.reactnativenavigation.layout;

import android.view.View;

public interface StackLayout extends Layout {
	public void push(View view);

	public void pop();
}
