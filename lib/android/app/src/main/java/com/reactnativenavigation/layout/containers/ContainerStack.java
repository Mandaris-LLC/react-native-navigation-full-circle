package com.reactnativenavigation.layout.containers;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.reactnativenavigation.layout.StackLayout;

import java.util.Stack;

public class ContainerStack extends FrameLayout implements StackLayout {

	private Stack<View> stack = new Stack<>();

	public ContainerStack(Context context) {
		super(context);
	}

	@Override
	public void push(View view) {
		addView(view);
		stack.push(getChildAt(0));
		removeView(getChildAt(0));
	}

	@Override
	public void pop() {
		addView(stack.pop());
		removeView(getChildAt(0));
	}
}
