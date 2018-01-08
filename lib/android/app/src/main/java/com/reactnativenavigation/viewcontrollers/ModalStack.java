package com.reactnativenavigation.viewcontrollers;

import android.app.Dialog;
import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.react.bridge.Promise;
import com.reactnativenavigation.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;

public class ModalStack {

	private List<Modal> modals = new ArrayList<>();

	public void showModal(final ViewController viewController, Promise promise) {
		Modal modal = new Modal(viewController);
		modals.add(modal);
		modal.show();
		if (promise != null) {
			promise.resolve(viewController.getId());
		}
	}

	public void dismissModal(final String componentId, Promise promise) {
		Modal modal = findModalByComponentId(componentId);
		if (modal != null) {
			modal.dismiss();
			modals.remove(modal);
			if (promise != null) {
				promise.resolve(componentId);
			}
		} else {
			Navigator.rejectPromise(promise);
		}
	}

	public void dismissAll(Promise promise) {
		for (Modal modal : modals) {
			modal.dismiss();
		}
		modals.clear();
		if (promise != null) {
			promise.resolve(true);
		}
	}

	@Nullable
	private Modal findModalByComponentId(String componentId) {
		for (Modal modal : modals) {
			if (modal.containsDeepComponentId(componentId)) {
				return modal;
			}
		}
		return null;
	}

	private static class Modal {
		private final ViewController viewController;
		private final Dialog dialog;

		Modal(final ViewController viewController) {
			this.viewController = viewController;
			dialog = new Dialog(viewController.getActivity(), R.style.Modal);
		}

		void show() {
			preMeasureView();
			dialog.setContentView(viewController.getView());
			dialog.show();
		}

		void dismiss() {
			dialog.dismiss();
		}

		boolean containsDeepComponentId(String componentId) {
			return viewController.findControllerById(componentId) != null;
		}

		private void preMeasureView() {
			View decorView = viewController.getActivity().getWindow().getDecorView();
			viewController.getView().measure(makeMeasureSpec(decorView.getMeasuredWidth(), EXACTLY), makeMeasureSpec(decorView.getMeasuredHeight(), EXACTLY));
		}
	}
}
