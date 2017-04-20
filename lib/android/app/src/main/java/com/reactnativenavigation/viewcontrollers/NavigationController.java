package com.reactnativenavigation.viewcontrollers;

import java.util.Stack;

public class NavigationController extends ViewController {
	private Stack<ViewController> childControllers = new Stack<>();

	public NavigationController(ViewController... childControllers) {
		for (ViewController childController : childControllers) {
			this.childControllers.push(childController);
		}
	}

	public Stack<ViewController> getChildControllers() {
		return childControllers;
	}

	public void push(final ViewController child) {
		childControllers.push(child);
	}

	public void pop() {
		childControllers.pop();
	}
}
