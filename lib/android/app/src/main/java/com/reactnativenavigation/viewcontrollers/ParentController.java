package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;

public abstract class ParentController extends ViewController {
	public ParentController(final Activity activity, final String id) {
		super(activity, id);
	}

	public abstract Iterable<ViewController> getChildControllers();

	public ViewController findControllerById(final String id) {
		ViewController fromSuper = super.findControllerById(id);
		if (fromSuper != null) {
			return fromSuper;
		}

		for (ViewController child : getChildControllers()) {
			ViewController found = child.findControllerById(id);
			if (found != null) return found;
		}

		return null;
	}
}
