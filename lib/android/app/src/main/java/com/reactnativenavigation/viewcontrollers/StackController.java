package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayDeque;

public class StackController extends ViewController {
	private ArrayDeque<ViewController> childControllers = new ArrayDeque<>();

	public StackController(final Activity activity) {
		super(activity);
	}

	@Override
	public ViewGroup getView() {
		return (ViewGroup) super.getView();
	}

	public ArrayDeque<ViewController> getChildControllers() {
		return childControllers;
	}

	public void setChildControllers(ViewController... childControllers) {
		for (ViewController childController : childControllers) {
			push(childController);
		}
	}

	public void push(final ViewController child) {
		child.setParentStackController(this);
		childControllers.push(child);
		getView().addView(child.getView());
	}

	public boolean canPop() {
		return childControllers.size() > 1;
	}

	public void pop() {
		if (canPop()) {
			childControllers.pop();
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
