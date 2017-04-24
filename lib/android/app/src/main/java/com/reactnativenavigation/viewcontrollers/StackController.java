package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayDeque;

public class StackController extends ViewController {
	private final ArrayDeque<ViewController> childControllers = new ArrayDeque<>();

	public StackController(final Activity activity) {
		super(activity);
	}

	public void push(final ViewController child) {
		ViewController previousTop = peek();

		child.setStackController(this);
		childControllers.push(child);
		getView().addView(child.getView());

		if (previousTop != null) {
			getView().removeView(previousTop.getView());
		}
	}

	public boolean canPop() {
		return childControllers.size() > 1;
	}

	public void pop() {
		if (!canPop()) {
			return;
		}
		ViewController poppedController = childControllers.pop();
		getView().removeView(poppedController.getView());

		ViewController previousTop = peek();
		getView().addView(previousTop.getView());
	}

	public void pop(final ViewController childController) {
		if (peek() == childController) {
			pop();
		} else {
			childControllers.remove(childController);
		}
	}

	public ViewController peek() {
		return childControllers.peek();
	}

	public int size() {
		return childControllers.size();
	}

	public boolean isEmpty() {
		return childControllers.isEmpty();
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
		return childControllers;
	}
}
