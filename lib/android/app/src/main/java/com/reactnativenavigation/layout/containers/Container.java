package com.reactnativenavigation.layout.containers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactContext;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.react.NavigationEventEmitter;

public class Container extends FrameLayout {
	private final ReactNativeHost reactNativeHost;
	private final String id;
	private final String name;

	public Container(Activity activity, ReactNativeHost reactNativeHost, String id, String name) {
		super(activity);
		this.reactNativeHost = reactNativeHost;
		this.id = id;
		this.name = name;
		addView(createReactRootView());

	}

	private View createReactRootView() {
		ReactRootView rootView = new ReactRootView(getContext());
		Bundle opts = new Bundle();
		opts.putString("id", id);
		rootView.startReactApplication(reactNativeHost.getReactInstanceManager(), name, opts);
		return rootView;
	}

	//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        NavigationEventEmitter.emit(NavigationApplication.instance.getReactNativeHost().getReactInstanceManager().getCurrentReactContext())
//                .containerStart(id);
//    }

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		//TODO this is wrong
		ReactContext reactContext = ((NavigationApplication) getContext().getApplicationContext()).getReactNativeHost().getReactInstanceManager().getCurrentReactContext();
		new NavigationEventEmitter(reactContext).containerStart(id);
	}
}
