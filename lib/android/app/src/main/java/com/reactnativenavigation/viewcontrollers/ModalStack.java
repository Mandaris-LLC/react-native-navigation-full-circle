package com.reactnativenavigation.viewcontrollers;

import android.app.Dialog;
import android.support.annotation.Nullable;
import android.view.View;

import com.reactnativenavigation.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;

public class ModalStack {

	private List<Modal> modals = new ArrayList<>();

	public void showModal(final ViewController viewController) {
		Modal modal = new Modal(viewController);
		modals.add(modal);
		modal.show();
	}

	public void dismissModal(final String containerId) {
		Modal modal = findModalByContainerId(containerId);
		if (modal != null) {
			modal.dismiss();
			modals.remove(modal);
		}
	}

	public void dismissAll() {
		for (Modal modal : modals) {
			modal.dismiss();
		}
		modals.clear();
	}

	@Nullable
	private Modal findModalByContainerId(String containerId) {
		for (Modal modal : modals) {
			if (modal.containsDeepContainerId(containerId)) {
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

		boolean containsDeepContainerId(String containerId) {
			return viewController.findControllerById(containerId) != null;
		}

		private void preMeasureView() {
			View decorView = viewController.getActivity().getWindow().getDecorView();
			viewController.getView().measure(makeMeasureSpec(decorView.getMeasuredWidth(), EXACTLY), makeMeasureSpec(decorView.getMeasuredHeight(), EXACTLY));
		}
	}
}
