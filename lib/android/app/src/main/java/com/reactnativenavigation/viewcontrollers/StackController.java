package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.reactnativenavigation.utils.StringUtils;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class StackController extends ViewController {
	private final ArrayDeque<String> idStack = new ArrayDeque<>();
	private final Map<String, ViewController> controllersById = new HashMap<>();

	public StackController(final Activity activity, String id) {
		super(activity, id);
	}

	public void push(final ViewController child) {
		final ViewController previousTop = peek();

		child.setStackController(this);
		idStack.push(child.getId());
		controllersById.put(child.getId(), child);

		getView().addView(child.getView());
		if (previousTop != null) {
			getView().removeView(previousTop.getView());
		}
	}

	public boolean canPop() {
		return idStack.size() > 1;
	}

	public void pop() {
		if (!canPop()) {
			return;
		}
		String poppedId = idStack.pop();
		ViewController poppedController = controllersById.remove(poppedId);
		getView().removeView(poppedController.getView());

		ViewController previousTop = peek();
		getView().addView(previousTop.getView());
	}

	public void pop(final ViewController childController) {
		if (StringUtils.isEqual(peekId(), childController.getId())) {
			pop();
		} else {
			idStack.remove(childController.getId());
			controllersById.remove(childController.getId());
		}
	}

	public ViewController peek() {
		return controllersById.get(peekId());
	}

	public String peekId() {
		return idStack.peek();
	}

	public int size() {
		return idStack.size();
	}

	public boolean isEmpty() {
		return idStack.isEmpty();
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
	public ViewGroup getView() {
		return (ViewGroup) super.getView();
	}

	@NonNull
	@Override
	protected ViewGroup createView() {
		return new FrameLayout(getActivity());
	}

	@Nullable
	@Override
	public StackController getStackController() {
		return this;
	}

	public void popTo(final ViewController viewController) {
		if (!idStack.contains(viewController.getId())) {
			return;
		}
		while (!StringUtils.isEqual(peekId(), viewController.getId())) {
			pop();
		}
	}

	public void popToRoot() {
		while (canPop()) {
			pop();
		}
	}

	public ViewController getChildById(final String id) {
		return controllersById.get(id);
	}

	ArrayDeque<String> getStack() {
		return idStack;
	}
}
