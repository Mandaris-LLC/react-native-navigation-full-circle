package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.reactnativenavigation.R;
import com.reactnativenavigation.utils.CompatUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;

public class Navigator extends ParentController {

	private ViewController root;
	private HashMap<ViewController, Dialog> modals = new HashMap<>();

	public Navigator(final Activity activity) {
		super(activity, "navigator" + CompatUtils.generateViewId());
	}

	@NonNull
	@Override
	protected ViewGroup createView() {
		return new FrameLayout(getActivity());
	}

	@Override
	public Collection<ViewController> getChildControllers() {
		return root == null ? Collections.<ViewController>emptyList() : Collections.singletonList(root);
	}

	@Override
	public boolean handleBack() {
		return root != null && root.handleBack();
	}

	/*
	 * Navigation methods
	 */

	public void setRoot(final ViewController viewController) {
		if (root != null) {
			root.destroy();
		}

		root = viewController;
		getView().addView(viewController.getView());
	}

	public void push(final String fromId, final ViewController viewController) {
		ViewController from = findControllerById(fromId);
		if (from != null) {
			StackController parentStackController = from.getParentStackController();
			if (parentStackController != null) {
				parentStackController.push(viewController);
			}
		}
	}

	public void pop(final String fromId) {
		ViewController from = findControllerById(fromId);
		if (from != null) {
			StackController parentStackController = from.getParentStackController();
			if (parentStackController != null) {
				parentStackController.pop();
			}
		}
	}

	public void popSpecific(final String id) {
		ViewController from = findControllerById(id);
		if (from != null) {
			StackController parentStackController = from.getParentStackController();
			if (parentStackController != null) {
				parentStackController.popSpecific(from);
			}
		}
	}

	public void popToRoot(final String id) {
		ViewController from = findControllerById(id);
		if (from != null) {
			StackController parentStackController = from.getParentStackController();
			if (parentStackController != null) {
				parentStackController.popToRoot();
			}
		}
	}

	public void popTo(final String containerId) {
		ViewController target = findControllerById(containerId);
		if (target != null) {
			StackController parentStackController = target.getParentStackController();
			if (parentStackController != null) {
				parentStackController.popTo(target);
			}
		}
	}

	public void showModal(final ViewController viewController) {
		viewController.getView().measure(makeMeasureSpec(getView().getWidth(), EXACTLY), makeMeasureSpec(getView().getHeight(), EXACTLY));
		Dialog dialog = new Dialog(getActivity(), R.style.Modal);
		dialog.setContentView(viewController.getView());
		dialog.show();
		modals.put(viewController, dialog);
	}

	public void dismissModal(final String containerId) {
		Pair<ViewController, Dialog> pair = findModalByContainerId(containerId);
		if (pair != null) {
			pair.second.dismiss();
			modals.remove(pair.first);
		}
	}

	@Nullable
	private Pair<ViewController, Dialog> findModalByContainerId(String containerId) {
		for (Map.Entry<ViewController, Dialog> entry : modals.entrySet()) {
			ViewController vc = entry.getKey().findControllerById(containerId);
			if (vc != null) {
				return Pair.create(entry.getKey(), entry.getValue());
			}
		}
		return null;
	}
}
