package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;

public abstract class ViewController {
	public interface LifecycleListener {
		void onCreate(ViewController viewController);

		void onStart(ViewController viewController);

		void onStop(ViewController viewController);

		void onDestroy(ViewController viewController);
	}

	private enum LifecycleState {
		Created, Started, Stopped, Destroyed
	}

	private final Activity activity;
	private View view;
	private StackController stackController;
	private LifecycleState lifecycleState;
	private LifecycleListener lifecycleListener;

	public ViewController(Activity activity) {
		this.activity = activity;
	}

	protected abstract View onCreateView();

	public boolean handleBack() {
		return false;
	}

	public Activity getActivity() {
		return activity;
	}

	@Nullable
	public StackController getStackController() {
		return stackController;
	}

	void setStackController(final StackController stackController) {
		this.stackController = stackController;
	}

	public View getView() {
		if (view == null) {
			view = createView();
		}
		return view;
	}

	public void setLifecycleListener(LifecycleListener lifecycleListener) {
		this.lifecycleListener = lifecycleListener;
	}

	private View createView() {
		View view = onCreateView();
		lifecycleState = LifecycleState.Created;
		view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (lifecycleListener != null) {
					if (lifecycleState != LifecycleState.Started) {
						lifecycleState = LifecycleState.Started;
						lifecycleListener.onStart(ViewController.this);
					}
				}
				return true;
			}
		});
		if (lifecycleListener != null) {
			lifecycleListener.onCreate(this);
		}
		return view;
	}
}
