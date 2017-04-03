package com.reactnativenavigation.layout;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.facebook.react.ReactNativeHost;
import com.reactnativenavigation.layout.containers.BottomTabs;
import com.reactnativenavigation.layout.containers.BottomTabsLayout;
import com.reactnativenavigation.layout.containers.Container;
import com.reactnativenavigation.layout.containers.ContainerStack;
import com.reactnativenavigation.layout.containers.SideMenuLayout;
import com.reactnativenavigation.utils.CompatUtils;

public class LayoutFactory {

	private final Activity activity;
	private ReactNativeHost reactNativeHost;

	public LayoutFactory(Activity activity, ReactNativeHost reactNativeHost) {
		this.activity = activity;
		this.reactNativeHost = reactNativeHost;
	}

	public View create(LayoutNode node) {
		switch (node.type) {
			case Container:
				return createContainer(node);
			case ContainerStack:
				return createContainerStack(node);
			case BottomTabs:
				return createBottomTabs(node);
			case SideMenuRoot:
				return createSideMenuRoot(node);
			case SideMenuCenter:
				return createSideMenuContent(node);
			case SideMenuLeft:
				return createSideMenuLeft(node);
			case SideMenuRight:
				return createSideMenuRight(node);
			default:
				throw new IllegalArgumentException("Invalid node type: " + node.type);
		}
	}

	private View createSideMenuRoot(LayoutNode node) {
		SideMenuLayout sideMenuLayout = new SideMenuLayout(activity);
		for (LayoutNode child : node.children) {
			sideMenuLayout.addView(create(child));
		}
		return sideMenuLayout;
	}

	private View createSideMenuContent(LayoutNode node) {
		return create(node.children.get(0));
	}

	private View createSideMenuLeft(LayoutNode node) {
		View view = create(node.children.get(0));
		view.setId(CompatUtils.generateViewId());
		DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.LEFT;
		view.setLayoutParams(lp);
		return view;
	}

	private View createSideMenuRight(LayoutNode node) {
		View view = create(node.children.get(0));
		view.setId(CompatUtils.generateViewId());
		DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.RIGHT;
		view.setLayoutParams(lp);
		return view;
	}

	private View createContainer(LayoutNode node) {
		final String name = node.data.optString("name");
		Container container = new Container(activity, node.id, name, reactNativeHost.getReactInstanceManager());
		container.setId(CompatUtils.generateViewId());
		return container;

	}

	private View createContainerStack(LayoutNode node) {
		final ContainerStack containerStack = new ContainerStack(activity);
		containerStack.setId(CompatUtils.generateViewId());
		for (LayoutNode child : node.children) {
			containerStack.addView(create(child));
		}
		return containerStack;
	}

	private View createBottomTabs(LayoutNode node) {
		final BottomTabsLayout tabsContainer = new BottomTabsLayout(activity, new BottomTabs());
		for (int i = 0; i < node.children.size(); i++) {
			final View tabContent = create(node.children.get(i));
			tabsContainer.addTabContent("#" + i, tabContent);
		}
		return tabsContainer;
	}

}
