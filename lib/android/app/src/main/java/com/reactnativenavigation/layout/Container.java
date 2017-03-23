package com.reactnativenavigation.layout;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;

import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.react.NavigationEventEmitter;

public class Container extends FrameLayout {
    private static final String TAG = "Container";
    private String id;

    public Container(Context context, LayoutFactory.ReactRootViewCreator reactRootViewCreator, String id, String name) {
		super(context);
        this.id = id;
        addView(reactRootViewCreator.create(id, name));

	}

//    @Override
//    protected void onAttachedToWindow() {
//        Log.d(TAG, "onAttachedToWindow: " + id);
//        super.onAttachedToWindow();
//        NavigationEventEmitter.emit(NavigationApplication.instance.getReactNativeHost().getReactInstanceManager().getCurrentReactContext())
//                .containerStart(id);
//    }

    @Override
    protected void onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow: " + id);
        super.onDetachedFromWindow();
        NavigationEventEmitter.emit(NavigationApplication.instance.getReactNativeHost().getReactInstanceManager().getCurrentReactContext())
                .containerStop(id);
    }
}
