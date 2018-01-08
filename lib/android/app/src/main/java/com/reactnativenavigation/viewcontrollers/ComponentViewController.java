package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.view.View;

import com.reactnativenavigation.parse.NavigationOptions;
import com.reactnativenavigation.presentation.NavigationOptionsListener;
import com.reactnativenavigation.views.ReactComponent;
import com.reactnativenavigation.views.TopBar;

public class ComponentViewController extends ViewController implements NavigationOptionsListener {

    public interface ReactViewCreator {

        IReactView create(Activity activity, String componentId, String componentName);
	}

	public interface IReactView {

		boolean isReady();

		View asView();

		void destroy();

		void sendComponentStart();

		void sendComponentStop();

        void sendOnNavigationButtonPressed(String buttonId);
    }

	private final String componentName;

	private final ReactViewCreator viewCreator;
	private NavigationOptions navigationOptions;
	private ReactComponent component;

	public ComponentViewController(final Activity activity,
								   final String id,
								   final String componentName,
								   final ReactViewCreator viewCreator,
								   final NavigationOptions initialNavigationOptions) {
		super(activity, id);
		this.componentName = componentName;
		this.viewCreator = viewCreator;
		this.navigationOptions = initialNavigationOptions;
	}

    @RestrictTo(RestrictTo.Scope.TESTS)
    TopBar getTopBar() {
        return component.getTopBar();
    }

	@Override
	public void destroy() {
		super.destroy();
		if (component != null) component.destroy();
		component = null;
	}

	@Override
	public void onViewAppeared() {
		super.onViewAppeared();
		ensureViewIsCreated();
		component.applyOptions(navigationOptions);
		component.sendComponentStart();
	}

	@Override
	public void onViewDisappear() {
		super.onViewDisappear();
		component.sendComponentStop();
	}

	@Override
	protected boolean isViewShown() {
		return super.isViewShown() && component.isReady();
	}

	@NonNull
	@Override
	protected View createView() {
		component = (ReactComponent) viewCreator.create(getActivity(), getId(), componentName);
        return component.asView();
	}

	@Override
	public void mergeNavigationOptions(NavigationOptions options) {
		navigationOptions.mergeWith(options);
        component.applyOptions(navigationOptions);
	}

	NavigationOptions getNavigationOptions() {
		return navigationOptions;
	}
}
