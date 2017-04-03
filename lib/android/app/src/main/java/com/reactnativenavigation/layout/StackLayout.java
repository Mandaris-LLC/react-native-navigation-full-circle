package com.reactnativenavigation.layout;

import android.view.View;

public interface StackLayout {
	void push(View view);

	void pop();
}
