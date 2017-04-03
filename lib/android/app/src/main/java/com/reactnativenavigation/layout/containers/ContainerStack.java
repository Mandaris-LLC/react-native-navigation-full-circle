package com.reactnativenavigation.layout.containers;

import android.content.Context;
import android.widget.FrameLayout;

import com.reactnativenavigation.layout.StackLayout;

import java.util.Stack;

public class ContainerStack extends FrameLayout implements StackLayout {

	private Stack<Container> stack = new Stack<>();

	public ContainerStack(Context context) {
		super(context);
	}


	@Override
	public void push(Container view) {
		stack.push(view);
		addView(view);
		if (stack.size() > 1) {
			Container previousTop = stack.elementAt(stack.size() - 2);
			removeView(previousTop);
		}
	}

	@Override
	public void pop() {
		Container top = stack.pop();
		removeView(top);
		top.destroy();
		if (!stack.isEmpty()) {
			Container previousTop = stack.peek();
			addView(previousTop);
		}
	}

	@Override
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
