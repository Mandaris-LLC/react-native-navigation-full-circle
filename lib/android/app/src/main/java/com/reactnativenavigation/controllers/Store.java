package com.reactnativenavigation.controllers;

import com.reactnativenavigation.layout.Layout;

import java.util.HashMap;
import java.util.Map;

public class Store {
	private Map<String, Layout> layoutsById = new HashMap<>();

	public void setLayout(String id, Layout layout) {
		layoutsById.put(id, layout);
	}

	public Layout getLayout(String id) {
		return layoutsById.get(id);
	}
}
