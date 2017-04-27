package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayDeque;

public class StackController extends ViewController {
	private final ArrayDeque<ViewController> stack = new ArrayDeque<>();

	public StackController(final Activity activity) {
		super(activity);
	}

	public void push(final ViewController child) {
		ViewController previousTop = peek();

		child.setStackController(this);
		stack.push(child);
		getView().addView(child.getView());

		if (previousTop != null) {
			getView().removeView(previousTop.getView());
		}
	}

	public boolean canPop() {
		return stack.size() > 1;
	}

	public void pop() {
		if (!canPop()) {
			return;
		}
		ViewController poppedController = stack.pop();
		getView().removeView(poppedController.getView());

		ViewController previousTop = peek();
		getView().addView(previousTop.getView());
	}

	public void pop(final ViewController childController) {
		if (peek() == childController) {
			pop();
		} else {
			stack.remove(childController);
		}
	}

	public ViewController peek() {
		return stack.peek();
	}

	public int size() {
		return stack.size();
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public boolean handleBack() {
		if (canPop()) {
			pop();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ViewGroup getView() {
		return (ViewGroup) super.getView();
	}

	@Override
	protected ViewGroup onCreateView() {
		return new FrameLayout(getActivity());
	}

	@Nullable
	@Override
	public StackController getStackController() {
		return this;
	}

	ArrayDeque<ViewController> getStack() {
		return stack;
	}

	public void popTo(final ViewController viewController) {
		if (!stack.contains(viewController)) {
			return;
		}
		while (peek() != viewController) {
			pop();
		}
	}

	public void popToRoot() {
		while (canPop()) {
			pop();
		}
	}
}
