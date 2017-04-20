package com.reactnativenavigation.viewcontrollers;

import java.util.ArrayDeque;

public class NavigationController extends ViewController {

	private ArrayDeque<ViewController> childControllers = new ArrayDeque<>();

	public NavigationController(ViewController... childControllers) {
		for (ViewController childController : childControllers) {
			push(childController);
		}
	}

	public ArrayDeque<ViewController> getChildControllers() {
		return childControllers;
	}

	public void push(final ViewController child) {
		child.setNavigationController(this);
		childControllers.push(child);
	}

	public void pop() {
		childControllers.pop();
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
		if (size() > 1) {
			pop();
			return true;
		} else {
			return false;
		}
	}
}
