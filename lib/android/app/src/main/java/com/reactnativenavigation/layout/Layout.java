package com.reactnativenavigation.layout;

import android.view.View;

import com.reactnativenavigation.layout.impl.StackLayout;

//TODO delete
public interface Layout {
	void setParentStackLayout(StackLayout stackLayout);

	StackLayout getParentStackLayout();

	View getView();

	void destroy();
}
