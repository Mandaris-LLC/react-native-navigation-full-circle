package com.reactnativenavigation.viewcontrollers;

import java.util.ArrayDeque;

public class NavigationController extends ViewController {

	private ArrayDeque<ViewController> childControllers = new ArrayDeque<>();

	public NavigationController(ViewController... childControllers) {
		for (ViewController childController : childControllers) {
			this.childControllers.push(childController);
		}
	}

	public ArrayDeque<ViewController> getChildControllers() {
		return childControllers;
	}

	public void push(final ViewController child) {
		childControllers.push(child);
	}

	public void pop() {
		childControllers.pop();
	}
}
