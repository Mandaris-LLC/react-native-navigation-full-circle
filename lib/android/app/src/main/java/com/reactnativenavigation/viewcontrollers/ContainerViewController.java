package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

public class ContainerViewController extends ViewController {

	public interface ContainerViewCreator {
		ContainerView create(Activity activity, String containerName, String containerId);
	}

	public interface ContainerView {
		boolean isReady();

		View asView();

		void destroy();

		void sendContainerStart();

		void sendContainerStop();
	}

	private final String containerName;
	private final ContainerViewCreator viewCreator;
	private ContainerView containerView;

	public ContainerViewController(final Activity activity,
	                               final String id,
	                               final String containerName,
	                               final ContainerViewCreator viewCreator) {
		super(activity, id);
		this.containerName = containerName;
		this.viewCreator = viewCreator;
	}

	@Override
	public void destroy() {
		super.destroy();
		if (containerView != null) containerView.destroy();
		containerView = null;
	}

	@Override
	public void onViewAppeared() {
		super.onViewAppeared();
		containerView.sendContainerStart();
	}

	@Override
	public void onViewDisappear() {
		super.onViewDisappear();
		containerView.sendContainerStop();
	}

	@Override
	protected boolean isViewShown() {
		return super.isViewShown() && containerView.isReady();
	}

	@NonNull
	@Override
	protected View createView() {
		containerView = viewCreator.create(getActivity(), containerName, getId());
		return containerView.asView();
	}
}
