package com.reactnativenavigation.layout;

import android.app.Activity;

import com.facebook.react.ReactInstanceManager;
import com.reactnativenavigation.controllers.Store;
import com.reactnativenavigation.layout.impl.BottomTabsLayout;
import com.reactnativenavigation.layout.impl.RootLayout;
import com.reactnativenavigation.layout.impl.SideMenuLayout;
import com.reactnativenavigation.layout.impl.StackLayout;

public class LayoutFactory {

	private final Activity activity;
	private ReactInstanceManager reactInstanceManager;
	private Store store;

	public LayoutFactory(Activity activity, final ReactInstanceManager reactInstanceManager, final Store store) {
		this.activity = activity;
		this.reactInstanceManager = reactInstanceManager;
		this.store = store;
	}

	public Layout createAndSaveToStore(LayoutNode node) {
		Layout layout = createLayout(node);
		store.setLayout(node.id, layout);
		return layout;
	}

	private Layout createLayout(final LayoutNode node) {
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

	private Layout createSideMenuRoot(LayoutNode node) {
		SideMenuLayout sideMenuLayout = new SideMenuLayout(activity);
		for (LayoutNode child : node.children) {
			Layout childLayout = createAndSaveToStore(child);
			switch (child.type) {
				case SideMenuCenter:
					sideMenuLayout.addCenterLayout(childLayout);
					break;
				case SideMenuLeft:
					sideMenuLayout.addLeftLayout(childLayout);
					break;
				case SideMenuRight:
					sideMenuLayout.addRightLayout(childLayout);
					break;
				default:
					throw new IllegalArgumentException("Invalid node type in sideMenu: " + node.type);
			}
		}
		return sideMenuLayout;
	}

	private Layout createSideMenuContent(LayoutNode node) {
		return createAndSaveToStore(node.children.get(0));
	}

	private Layout createSideMenuLeft(LayoutNode node) {
		return createAndSaveToStore(node.children.get(0));
	}

	private Layout createSideMenuRight(LayoutNode node) {
		return createAndSaveToStore(node.children.get(0));
	}

	private Layout createContainer(LayoutNode node) {
		return new RootLayout(activity, node.id, node.data.optString("name"), reactInstanceManager);
	}

	private Layout createContainerStack(LayoutNode node) {
		final StackLayout layoutStack = new StackLayout(activity);
		for (LayoutNode child : node.children) {
			layoutStack.push(createAndSaveToStore(child));
		}
		return layoutStack;
	}

	private Layout createBottomTabs(LayoutNode node) {
		final BottomTabsLayout tabsContainer = new BottomTabsLayout(activity);
		for (int i = 0; i < node.children.size(); i++) {
			final Layout tabLayout = createAndSaveToStore(node.children.get(i));
			tabsContainer.addTab("#" + i, tabLayout);
		}
		return tabsContainer;
	}

}
