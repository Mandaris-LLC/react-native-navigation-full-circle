package com.reactnativenavigation;

import com.reactnativenavigation.viewcontrollers.ViewController;

import java.util.HashMap;
import java.util.Map;

public class Store {
	private Map<String, ViewController> viewControllersById = new HashMap<>();

	public void setViewController(String id, ViewController viewController) {
		viewControllersById.put(id, viewController);
	}

	public ViewController getViewController(String id) {
		return viewControllersById.get(id);
	}
}
