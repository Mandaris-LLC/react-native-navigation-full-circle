package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;

import com.reactnativenavigation.utils.StringUtils;

public abstract class ViewController implements ViewTreeObserver.OnGlobalLayoutListener {

	private final Activity activity;
	private final String id;
	private View view;
	private StackController parentStackController;
	private boolean isShown = false;

	public ViewController(Activity activity, String id) {
		this.activity = activity;
		this.id = id;
	}

	@NonNull
	protected abstract View createView();

	public boolean handleBack() {
		return false;
	}

	public Activity getActivity() {
		return activity;
	}

	@Nullable
	public StackController getParentStackController() {
		return parentStackController;
	}

	void setParentStackController(final StackController parentStackController) {
		this.parentStackController = parentStackController;
	}

	@NonNull
	public View getView() {
		if (view == null) {
			view = createView();
			view.getViewTreeObserver().addOnGlobalLayoutListener(this);
		}
		return view;
	}

	public String getId() {
		return id;
	}

	public boolean isSameId(final String id) {
		return StringUtils.isEqual(this.id, id);
	}

	@Nullable
	public ViewController findControllerById(String id) {
		return isSameId(id) ? this : null;
	}

	public void onAppear() {
		//
	}

	public void onDisappear() {
		//
	}

	public void onDestroy() {
		view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
		view = null;
	}

	@Override
	public void onGlobalLayout() {
		if (!isShown && isViewShown()) {
			isShown = true;
			onAppear();
		} else if (isShown && !isViewShown()) {
			isShown = false;
			onDisappear();
		}
	}

	protected boolean isViewShown() {
		return getView().isShown();
	}
}
