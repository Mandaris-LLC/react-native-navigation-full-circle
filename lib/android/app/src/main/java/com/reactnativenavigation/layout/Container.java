package com.reactnativenavigation.layout;

import android.app.Activity;
import android.widget.FrameLayout;

import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.react.NavigationEventEmitter;

public class Container extends FrameLayout {
	private final String id;

	public Container(Activity activity, LayoutFactory.ReactRootViewCreator reactRootViewCreator, String id, String name) {
		super(activity);
		this.id = id;
		addView(reactRootViewCreator.create(activity, id, name));
	}

	public String getContainerId() {
		return id;
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
		NavigationEventEmitter.emit(NavigationApplication.instance.getReactNativeHost().getReactInstanceManager().getCurrentReactContext())
				.containerStop(id);
	}
}
