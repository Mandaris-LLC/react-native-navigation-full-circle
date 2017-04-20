package com.reactnativenavigation.layout.impl;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.reactnativenavigation.layout.Layout;
import com.reactnativenavigation.utils.CompatUtils;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class SideMenuLayout implements Layout {

	private DrawerLayout view;
	private StackLayout stackLayout;

	public SideMenuLayout(Context context) {
		view = new DrawerLayout(context);
		view.setId(CompatUtils.generateViewId());
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public void destroy() {
		//
	}

	public void addLeftLayout(final Layout childLayout) {
		View child = childLayout.getView();
		DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
		lp.gravity = Gravity.LEFT;
		child.setLayoutParams(lp);
		view.addView(child);
	}

	public void addRightLayout(final Layout childLayout) {
		View child = childLayout.getView();
		DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
		lp.gravity = Gravity.RIGHT;
		child.setLayoutParams(lp);
		view.addView(child);
	}

	public void addCenterLayout(final Layout childLayout) {
		View child = childLayout.getView();
		view.addView(child);
	}

	@Override
	public void setParentStackLayout(final StackLayout stackLayout) {
		this.stackLayout = stackLayout;
	}

	@Override
	public StackLayout getParentStackLayout() {
		return stackLayout;
	}
	
}
