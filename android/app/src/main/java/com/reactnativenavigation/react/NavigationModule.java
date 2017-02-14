package com.reactnativenavigation.react;

import android.os.Bundle;
import android.view.View;

import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.reactnativenavigation.controllers.NavigationActivity;
import com.reactnativenavigation.layout.LayoutFactory;
import com.reactnativenavigation.layout.LayoutNode;
import com.reactnativenavigation.layout.StackLayout;
import com.reactnativenavigation.layout.bottomtabs.BottomTabsCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NavigationModule extends ReactContextBaseJavaModule {
    public static final String NAME = "RNNBridgeModule";

    public NavigationModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void setRoot(final ReadableMap layoutTree) {
        NavigationActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LayoutFactory factory =
                        new LayoutFactory(NavigationActivity.instance, new LayoutFactory.ReactRootViewCreator() {
                            @Override
                            public View create(String id, String name) {
                                ReactRootView rootView = new ReactRootView(NavigationActivity.instance);
                                Bundle opts = new Bundle();
                                opts.putString("id", id);
                                rootView.startReactApplication(NavigationActivity.instance.getHost().getReactInstanceManager(), name, opts);
                                return rootView;
                            }
                        }, new BottomTabsCreator());

                final LayoutNode layoutTreeRoot = readableMapToLayoutNode(layoutTree);
                final View rootView = factory.create(layoutTreeRoot);
                NavigationActivity.instance.setRoot((StackLayout) rootView);
            }
        });
    }

    @ReactMethod
    public void push(String onContainerId, final ReadableMap layout) {
        NavigationActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LayoutFactory factory =
                        new LayoutFactory(NavigationActivity.instance, new LayoutFactory.ReactRootViewCreator() {
                            @Override
                            public View create(String id, String name) {
                                ReactRootView rootView = new ReactRootView(NavigationActivity.instance);
                                Bundle opts = new Bundle();
                                opts.putString("id", id);
                                rootView.startReactApplication(NavigationActivity.instance.getHost().getReactInstanceManager(), name, opts);
                                return rootView;
                            }
                        }, new BottomTabsCreator());
                final LayoutNode layoutNode = readableMapToLayoutNode(layout);
                final View rootView = factory.create(layoutNode);
                NavigationActivity.instance.push(rootView);
            }
        });
    }

    @ReactMethod
    public void pop(String onContainerId) {
        NavigationActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NavigationActivity.instance.pop();
            }
        });
    }

    private LayoutNode readableMapToLayoutNode(ReadableMap readableMap) {
        final LayoutNode layoutNode = new LayoutNode();
        layoutNode.id = readableMap.getString("id");
        layoutNode.type = readableMap.getString("type");
        layoutNode.data = readableMapToJavaMap(readableMap.getMap("data"));

        ReadableArray childrenNodes = readableMap.getArray("children");
        layoutNode.children = new ArrayList<>(childrenNodes.size());
        for (int i = 0; i < childrenNodes.size(); i++) {
            ReadableMap child = childrenNodes.getMap(i);
            layoutNode.children.add(readableMapToLayoutNode(child));
        }

        return layoutNode;
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
