package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.concurrent.atomic.AtomicReference;

public abstract class ViewController {
	public interface LifecycleListener {
		void onCreate();

		void onStart();

		void onStop();

		void onDestroy();
	}

	private enum LifecycleState {
		Created, Started, Stopped, Destroyed
	}

	private final Activity activity;
	private View view;
	private StackController stackController;
	private AtomicReference<LifecycleState> lifecycleState = new AtomicReference<>(LifecycleState.Destroyed);
	private LifecycleListener lifecycleListener;

	public ViewController(Activity activity) {
		this.activity = activity;
	}

	protected abstract View createView();

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
			attachLifecycle();
		}
		return view;
	}

	public void setLifecycleListener(LifecycleListener lifecycleListener) {
		this.lifecycleListener = lifecycleListener;
	}

	private void attachLifecycle() {
		view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				if (lifecycleListener == null) {
					return;
				}
				if (view.getVisibility() == View.VISIBLE) {
					if (lifecycleState.compareAndSet(LifecycleState.Created, LifecycleState.Started)) {
						lifecycleListener.onStart();
					}
				} else {
					if (lifecycleState.compareAndSet(LifecycleState.Started, LifecycleState.Stopped)) {
						lifecycleListener.onStop();
					}
				}
			}
		});
		lifecycleState.set(LifecycleState.Created);
		if (lifecycleListener != null) {
			lifecycleListener.onCreate();
		}
	}
}
