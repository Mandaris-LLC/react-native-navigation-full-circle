package com.reactnativenavigation.layout.containers;

import android.content.Context;
import android.widget.FrameLayout;

import java.util.Stack;

public class ContainerStack extends FrameLayout {

	private Stack<Container> stack = new Stack<>();

	public ContainerStack(Context context) {
		super(context);
	}

	public void push(Container view) {
		stack.push(view);
		addView(view);
		if (stack.size() > 1) {
			Container previousTop = stack.elementAt(stack.size() - 2);
			removeView(previousTop);
		}
	}

	public void pop() {
		Container top = stack.pop();
		removeView(top);
		top.destroy();
		if (!stack.isEmpty()) {
			Container previousTop = stack.peek();
			addView(previousTop);
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

	public boolean isEmpty() {
		return stack.isEmpty();
	}
}
