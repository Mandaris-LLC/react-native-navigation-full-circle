package com.reactnativenavigation.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.RestrictTo;
import android.view.View;
import android.widget.LinearLayout;

import com.reactnativenavigation.parse.NavigationOptions;
import com.reactnativenavigation.presentation.OptionsPresenter;
import com.reactnativenavigation.viewcontrollers.ComponentViewController.IReactView;

@SuppressLint("ViewConstructor")
public class ComponentLayout extends LinearLayout implements ReactComponent {

	private TopBar topBar;
	private IReactView reactView;
	private final OptionsPresenter optionsPresenter;

	public ComponentLayout(Context context, IReactView reactView) {
		super(context);
		this.topBar = new TopBar(context, this);
		this.reactView = reactView;
        optionsPresenter = new OptionsPresenter(topBar, reactView.asView());
        initViews();
	}

	private void initViews() {
	    setOrientation(VERTICAL);
		addView(topBar);
		addView(reactView.asView());
	}

	@Override
	public boolean isReady() {
		return reactView.isReady();
	}

	@Override
	public View asView() {
		return this;
	}

	@Override
	public void destroy() {
		reactView.destroy();
	}

	@Override
	public void sendComponentStart() {
		reactView.sendComponentStart();
	}

	@Override
	public void sendComponentStop() {
		reactView.sendComponentStop();
	}

    @Override
    public void applyOptions(NavigationOptions options) {
        optionsPresenter.applyOptions(options);
    }

	@Override
    public void sendOnNavigationButtonPressed(String buttonId) {
        reactView.sendOnNavigationButtonPressed(buttonId);
    }

    @Override
    @RestrictTo(RestrictTo.Scope.TESTS)
    public TopBar getTopBar() {
        return topBar;
    }
}
