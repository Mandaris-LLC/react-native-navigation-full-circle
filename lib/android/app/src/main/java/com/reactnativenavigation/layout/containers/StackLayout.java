package com.reactnativenavigation.layout.containers;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.reactnativenavigation.layout.Layout;
import com.reactnativenavigation.utils.CompatUtils;

import java.util.Stack;

public class StackLayout implements Layout {

	private final Stack<Layout> stack = new Stack<>();
	private final FrameLayout view;

	public StackLayout(Context context) {
		view = new FrameLayout(context);
		view.setId(CompatUtils.generateViewId());
	}

	public void push(Layout child) {
		stack.push(child);
		view.addView(child.getView());
		if (stack.size() > 1) {
			Layout previousTop = stack.elementAt(stack.size() - 2);
			view.removeView(previousTop.getView());
		}
	}

	public void pop() {
		Layout top = stack.pop();
		view.removeView(top.getView());
		top.destroy();
		if (!stack.isEmpty()) {
			Layout previousTop = stack.peek();
			view.addView(previousTop.getView());
		}
	}

	public boolean onBackPressed() {
		if (stack.isEmpty()) {
			return false;
		} else {
			pop();
			return true;
		}
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public void destroy() {
		//
	}
}
