package com.reactnativenavigation.controllers;

import android.view.View;

import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.layout.LayoutFactory;
import com.reactnativenavigation.layout.LayoutNode;
import com.reactnativenavigation.layout.StackLayout;
import com.reactnativenavigation.react.NavigationReactRootViewCreator;

import org.json.JSONObject;

public class CommandsHandler {

	public void setRoot(final NavigationActivity activity, final JSONObject layoutTree) {
		final LayoutNode layoutTreeRoot = LayoutNode.parse(layoutTree);
		LayoutFactory factory =
				new LayoutFactory(activity, new NavigationReactRootViewCreator());

		final View rootView = factory.create(layoutTreeRoot);
		activity.setContentView(rootView);
	}

	public void push(final NavigationActivity activity, String onContainerId, JSONObject layoutTree) {
		final LayoutNode layoutNode = LayoutNode.parse(layoutTree);
		LayoutFactory factory =
				new LayoutFactory(activity, new NavigationReactRootViewCreator());
		final View rootView = factory.create(layoutNode);
		((StackLayout) activity.getContentView()).push(rootView);
	}

	public void pop(final NavigationActivity activity, String onContainerId) {
		((StackLayout) activity.getContentView()).pop();
	}
}
