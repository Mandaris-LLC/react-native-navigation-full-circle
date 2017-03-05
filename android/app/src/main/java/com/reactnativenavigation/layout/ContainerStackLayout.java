package com.reactnativenavigation.layout;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Stack;

public class ContainerStackLayout extends FrameLayout implements StackLayout {

	private Stack<View> backStack = new Stack<>();

	public ContainerStackLayout(Context context) {
		super(context);
	}

	@Override
	public void push(View view) {
		addView(view);
		backStack.push(getChildAt(0));
		removeView(getChildAt(0));
	}

	@Override
	public void pop() {
		addView(backStack.pop());
		removeView(getChildAt(0));
	}

	@Override
	public View asView() {
		return this;
	}
}
