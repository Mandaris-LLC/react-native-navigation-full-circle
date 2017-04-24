package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.reactnativenavigation.utils.Stack;
import com.reactnativenavigation.utils.StackImpl;

public class StackController extends ViewController {
	private Stack<ViewController> childControllers = new StackImpl<>();

	public StackController(final Activity activity) {
		super(activity);
	}

	@Override
	public ViewGroup getView() {
		return (ViewGroup) super.getView();
	}

	public Stack<ViewController> getChildControllers() {
		return childControllers;
	}

	public void setChildControllers(ViewController... childControllers) {
		for (ViewController childController : childControllers) {
			push(childController);
		}
	}

	public void push(final ViewController child) {
		ViewController previousTop = peek();

		child.setParentStackController(this);
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
	protected ViewGroup onCreateView() {
		return new FrameLayout(getActivity());
	}
}
