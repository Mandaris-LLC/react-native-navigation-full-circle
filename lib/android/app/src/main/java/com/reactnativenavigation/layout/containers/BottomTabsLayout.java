package com.reactnativenavigation.layout.containers;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

import com.reactnativenavigation.layout.Layout;
import com.reactnativenavigation.utils.CompatUtils;

import java.util.ArrayList;
import java.util.List;

import static android.widget.RelativeLayout.ABOVE;

public class BottomTabsLayout implements Layout, BottomTabs.BottomTabsSelectionListener {

	public static class TooManyTabs extends RuntimeException {
	}

	private final RelativeLayout view;
	private final BottomTabs bottomTabs;
	private final List<View> tabContentViews = new ArrayList<>();
	private int currentTab;

	public BottomTabsLayout(Activity activity) {
		view = new RelativeLayout(activity);
		view.setId(CompatUtils.generateViewId());

		//TODO inline everything. unneeded complexity
		bottomTabs = new BottomTabs();
		bottomTabs.attach(view);
		bottomTabs.setSelectionListener(this);
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public void destroy() {
		//
	}


	public void addTabContent(String label, Layout tabContent) {
		if (tabContentViews.size() >= 5) {
			throw new TooManyTabs();
		}
		bottomTabs.add(label);
		attachTabContent(tabContent.getView());
		tabContentViews.add(tabContent.getView());

		if (tabContentViews.size() > 1) {
			tabContent.getView().setVisibility(View.GONE);
		}
	}

	@Override
	public void onTabSelected(int index) {
		hideTab(currentTab);
		currentTab = index;
		showTab(currentTab);
	}

	private void attachTabContent(View tabContent) {
		RelativeLayout.LayoutParams tabParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		tabParams.addRule(ABOVE, bottomTabs.getViewId());
		view.addView(tabContent, tabParams);
	}

	private void showTab(int tabId) {
		tabContentViews.get(tabId).setVisibility(View.VISIBLE);
	}

	private void hideTab(int tabId) {
		tabContentViews.get(tabId).setVisibility(View.GONE);
	}
}
