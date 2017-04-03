package com.reactnativenavigation.layout;

import com.reactnativenavigation.layout.containers.Container;

public interface StackLayout {
	void push(Container view);

	void pop();

	boolean onBackPressed();
}
