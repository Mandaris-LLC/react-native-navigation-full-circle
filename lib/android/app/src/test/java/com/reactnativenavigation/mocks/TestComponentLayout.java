package com.reactnativenavigation.mocks;

import android.content.Context;
import android.view.View;

import com.reactnativenavigation.parse.NavigationOptions;
import com.reactnativenavigation.presentation.OptionsPresenter;
import com.reactnativenavigation.views.ReactComponent;
import com.reactnativenavigation.views.TopBar;

public class TestComponentLayout extends View implements ReactComponent {

    private final TopBar topBar;
    private final OptionsPresenter optionsPresenter;

    public TestComponentLayout(final Context context) {
		super(context);
        topBar = new TopBar(context, this);
        optionsPresenter = new OptionsPresenter(topBar, new View(context));
    }

    public TopBar getTopBar() {
        return topBar;
    }

    @Override
	public boolean isReady() {
		return false;
	}

	@Override
	public View asView() {
		return this;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void sendComponentStart() {
	}

	@Override
	public void sendComponentStop() {
	}

    @Override
    public void applyOptions(NavigationOptions options) {
        optionsPresenter.applyOptions(options);
    }

    @Override
    public void sendOnNavigationButtonPressed(String id) {

    }
}
