package com.reactnativenavigation.controllers;

import android.os.Bundle;
import android.view.View;

import com.facebook.react.ReactRootView;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.layout.LayoutFactory;
import com.reactnativenavigation.layout.StackLayout;
import com.reactnativenavigation.layout.bottomtabs.BottomTabsCreator;
import com.reactnativenavigation.layout.parse.LayoutNode;
import com.reactnativenavigation.utils.UiThread;

import org.json.JSONObject;

public class CommandsHandler {

	public void setRoot(final NavigationActivity activity, final JSONObject layoutTree) {
		LayoutFactory factory =
				new LayoutFactory(activity, new LayoutFactory.ReactRootViewCreator() {
					@Override
					public View create(String id, String name) {
						ReactRootView rootView = new ReactRootView(activity);
						Bundle opts = new Bundle();
						opts.putString("id", id);
						rootView.startReactApplication(NavigationApplication.instance.getReactNativeHost().getReactInstanceManager(), name, opts);
						return rootView;
					}
				}, new BottomTabsCreator());

		final LayoutNode layoutTreeRoot = LayoutNode.fromTree(layoutTree);
		final View rootView = factory.create(layoutTreeRoot);
		activity.setContentView(rootView);
	}

	public void push(final NavigationActivity activity, String onContainerId, JSONObject layoutTree) {
		LayoutFactory factory =
				new LayoutFactory(activity, new LayoutFactory.ReactRootViewCreator() {
					@Override
					public View create(String id, String name) {
						ReactRootView rootView = new ReactRootView(activity);
						Bundle opts = new Bundle();
						opts.putString("id", id);
						rootView.startReactApplication(NavigationApplication.instance.getReactNativeHost().getReactInstanceManager(), name, opts);
						return rootView;
					}
				}, new BottomTabsCreator());
		final LayoutNode layoutNode = LayoutNode.fromTree(layoutTree);
		final View rootView = factory.create(layoutNode);
		((StackLayout) activity.getContentView()).push(rootView);
	}

	public void pop(final NavigationActivity activity, String onContainerId) {
		UiThread.post(new Runnable() {
			@Override
			public void run() {
				((StackLayout) activity.getContentView()).pop();
			}
		});
	}
}
