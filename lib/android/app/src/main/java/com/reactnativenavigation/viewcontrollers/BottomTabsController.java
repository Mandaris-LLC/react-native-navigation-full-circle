package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.reactnativenavigation.utils.CompatUtils;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.RelativeLayout.ABOVE;
import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;

public class BottomTabsController extends ViewController implements BottomNavigationView.OnNavigationItemSelectedListener {
	private BottomNavigationView bottomNavigationView;
	private int selectedIndex = 0;
	private List<ViewController> tabs;

	public BottomTabsController(final Activity activity, final String id) {
		super(activity, id);
	}

	@NonNull
	@Override
	protected RelativeLayout createView() {
		RelativeLayout root = new RelativeLayout(getActivity());
		bottomNavigationView = new BottomNavigationView(getActivity());
		bottomNavigationView.setId(CompatUtils.generateViewId());
		bottomNavigationView.setBackgroundColor(Color.DKGRAY);
		bottomNavigationView.setOnNavigationItemSelectedListener(this);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
		lp.addRule(ALIGN_PARENT_BOTTOM);
		root.addView(bottomNavigationView, lp);
		return root;
	}

	@NonNull
	@Override
	public RelativeLayout getView() {
		return (RelativeLayout) super.getView();
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
		selectedIndex = item.getItemId();
		for (ViewController tab : tabs) {
			tab.getView().setVisibility(View.GONE);
		}
		tabs.get(selectedIndex).getView().setVisibility(View.VISIBLE);
		return true;
	}

	public void setTabs(final List<ViewController> tabs) {
		if (tabs.size() > 5) {
			throw new RuntimeException("Too many tabs!");
		}
		this.tabs = tabs;
		getView();
		for (int i = 0; i < tabs.size(); i++) {
			ViewController tab = tabs.get(i);
			bottomNavigationView.getMenu().add(0, i, Menu.NONE, String.valueOf(i));
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
			params.addRule(ABOVE, bottomNavigationView.getId());
			getView().addView(tab.getView(), params);
			if (i > 0) {
				tab.getView().setVisibility(View.GONE);
			}
		}
	}

	public List<ViewController> getTabs() {
		return tabs;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}
}
