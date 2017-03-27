package com.reactnativenavigation.controllers;

import android.view.View;

import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.layout.LayoutFactory;
import com.reactnativenavigation.layout.LayoutNode;
import com.reactnativenavigation.layout.StackLayout;
import com.reactnativenavigation.react.ReactRootViewCreator;

import org.json.JSONObject;

public class CommandsHandler {

	private final ReactRootViewCreator reactRootViewCreator;

	public CommandsHandler(ReactRootViewCreator reactRootViewCreator) {
		this.reactRootViewCreator = reactRootViewCreator;
	}

	public void setRoot(final NavigationActivity activity, final JSONObject layoutTree) {
		final LayoutNode layoutTreeRoot = LayoutNode.parse(layoutTree);
		LayoutFactory factory = new LayoutFactory(activity, reactRootViewCreator);
		final View rootView = factory.create(layoutTreeRoot);
		activity.setContentView(rootView);
	}

	public void push(final NavigationActivity activity, String onContainerId, JSONObject layoutTree) {
		final LayoutNode layoutNode = LayoutNode.parse(layoutTree);
		LayoutFactory factory = new LayoutFactory(activity, reactRootViewCreator);
		final View rootView = factory.create(layoutNode);
		((StackLayout) activity.getContentView()).push(rootView);
	}

	public void pop(final NavigationActivity activity, String onContainerId) {
		((StackLayout) activity.getContentView()).pop();
	}
}
