package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.reactnativenavigation.anim.StackAnimator;

import java.util.Collection;

public class StackController extends ParentController {
	private final IdStack<ViewController> stack = new IdStack<>();
	private StackAnimator animator;

	public StackController(final Activity activity, String id) {
		this(activity, id, new StackAnimator(activity));
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
			animator.animatePush(child.getView(), previousTop.getView(), new Runnable() {
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
		if (!canPop()) return;

		final ViewController poppedTop = stack.pop();
		ViewController newTop = peek();

		getView().addView(newTop.getView());

		animator.animatePop(newTop.getView(), poppedTop.getView(), new Runnable() {
			@Override
			public void run() {
				getView().removeView(poppedTop.getView());
				poppedTop.destroy();
			}
		});
	}

	public void popSpecific(final ViewController childController) {
		if (stack.isTop(childController.getId())) {
			pop();
		} else {
			stack.remove(childController.getId());
			childController.destroy();
		}
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

	@NonNull
	@Override
	public Collection<ViewController> getChildControllers() {
		return stack.values();
	}
}
