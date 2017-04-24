package com.reactnativenavigation.controllers;

import com.reactnativenavigation.viewcontrollers.ViewController;

import java.util.HashMap;
import java.util.Map;

public class Store {
	private Map<String, ViewController> viewControllersById = new HashMap<>();

	public void setViewController(String id, ViewController layout) {
		viewControllersById.put(id, layout);
	}

	public ViewController getViewController(String id) {
		return viewControllersById.get(id);
	}
}
