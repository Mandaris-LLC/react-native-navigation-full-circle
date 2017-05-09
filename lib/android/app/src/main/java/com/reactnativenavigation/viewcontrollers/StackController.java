package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.reactnativenavigation.anim.StackAnimator;

import java.util.Collection;

public class StackController extends ParentController {
	private final IndexedStack<ViewController> stack = new IndexedStack<>();
	private StackAnimator animator;

	public StackController(final Activity activity, String id) {
		this(activity, id, new StackAnimator());
	}

	public StackController(final Activity activity, String id, StackAnimator animator) {
		super(activity, id);
		this.animator = animator;
	}

	public void push(final ViewController child) {
		final ViewController previousTop = peek();

		child.setParentStackController(this);
		stack.push(child.getId(), child);

		getView().addView(child.getView());

		if (previousTop != null) {
			animator.animatePush(child.getView(), new Runnable() {
				@Override
				public void run() {
					getView().removeView(previousTop.getView());
				}
			});
		}
	}

	public boolean canPop() {
		return stack.size() > 1;
	}

	public void pop() {
		if (!canPop()) {
			return;
		}
		ViewController poppedController = stack.pop();
		getView().removeView(poppedController.getView());

		ViewController previousTop = peek();
		getView().addView(previousTop.getView());
	}

	public void popSpecific(final ViewController childController) {
		if (stack.isTop(childController.getId())) {
			pop();
		} else {
			stack.remove(childController.getId());
		}
	}

	public ViewController peek() {
		return stack.peek();
	}

	public int size() {
		return stack.size();
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public boolean handleBack() {
		if (canPop()) {
			pop();
			return true;
		} else {
			return false;
		}
	}

	@NonNull
	@Override
	protected ViewGroup createView() {
		return new FrameLayout(getActivity());
	}

	@Nullable
	@Override
	public StackController getParentStackController() {
		return this;
	}

	public void popTo(final ViewController viewController) {
		if (!stack.containsId(viewController.getId())) {
			return;
		}
		while (!stack.isTop(viewController.getId())) {
			pop();
		}
	}

	public void popToRoot() {
		while (canPop()) {
			pop();
		}
	}

	boolean containsId(String id) {
		return stack.containsId(id);
	}

	@NonNull
	@Override
	public Collection<ViewController> getChildControllers() {
		return stack.values();
	}
}
