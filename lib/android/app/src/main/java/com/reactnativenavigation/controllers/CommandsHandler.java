package com.reactnativenavigation.controllers;

import android.os.Bundle;
import android.view.View;

import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.layout.LayoutFactory;
import com.reactnativenavigation.layout.StackLayout;
import com.reactnativenavigation.layout.bottomtabs.BottomTabsCreator;
import com.reactnativenavigation.layout.parse.LayoutNode;
import com.reactnativenavigation.utils.UiThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandsHandler {

	public void setRoot(final NavigationActivity activity, final ReadableMap layoutTree) {
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

		final LayoutNode layoutTreeRoot = readableMapToLayoutNode(layoutTree);
		final View rootView = factory.create(layoutTreeRoot);
		activity.setContentView(rootView);
	}

	public void push(final NavigationActivity activity, String onContainerId, final ReadableMap layout) {
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
		final LayoutNode layoutNode = readableMapToLayoutNode(layout);
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

	private LayoutNode readableMapToLayoutNode(ReadableMap readableMap) {
		String id = readableMap.getString("id");
		LayoutNode.Type type = LayoutNode.Type.fromString(readableMap.getString("type"));
		Map<String, Object> data = readableMapToJavaMap(readableMap.getMap("data"));

		ReadableArray childrenNodes = readableMap.getArray("children");
		List<LayoutNode> children = new ArrayList<>(childrenNodes.size());
		for (int i = 0; i < childrenNodes.size(); i++) {
			ReadableMap child = childrenNodes.getMap(i);
			children.add(readableMapToLayoutNode(child));
		}

		return new LayoutNode(id, type, data, children);
	}

	private Map<String, Object> readableMapToJavaMap(ReadableMap readableMap) {
		final Map<String, Object> map = new HashMap<>();
		for (ReadableMapKeySetIterator it = readableMap.keySetIterator(); it.hasNextKey(); ) {
			final String key = it.nextKey();
			switch (readableMap.getType(key)) {
				case String:
					map.put(key, readableMap.getString(key));
					break;
				case Map:
					map.put(key, readableMapToJavaMap(readableMap.getMap(key)));
					break;
			}
		}
		return map;
	}
}
