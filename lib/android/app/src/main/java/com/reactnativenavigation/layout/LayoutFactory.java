package com.reactnativenavigation.layout;

import android.app.Activity;

import com.facebook.react.ReactInstanceManager;
import com.reactnativenavigation.Store;
import com.reactnativenavigation.layout.impl.ReactRootViewController;
import com.reactnativenavigation.viewcontrollers.StackController;
import com.reactnativenavigation.viewcontrollers.ViewController;

public class LayoutFactory {

	private final Activity activity;
	private final ReactInstanceManager reactInstanceManager;
	private final Store store;

	public LayoutFactory(Activity activity, final ReactInstanceManager reactInstanceManager, final Store store) {
		this.activity = activity;
		this.reactInstanceManager = reactInstanceManager;
		this.store = store;
	}

	public ViewController createAndSaveToStore(LayoutNode node) {
		ViewController viewController = create(node);
		store.setViewController(node.id, viewController);
		return viewController;
	}

	private ViewController create(final LayoutNode node) {
		switch (node.type) {
			case Container:
				return createContainer(node);
			case ContainerStack:
			default:
				return createContainerStack(node);
//			case BottomTabs:
//				return createBottomTabs(node);
//			case SideMenuRoot:
//				return createSideMenuRoot(node);
//			case SideMenuCenter:
//				return createSideMenuContent(node);
//			case SideMenuLeft:
//				return createSideMenuLeft(node);
//			case SideMenuRight:
//				return createSideMenuRight(node);
//			default:
//				throw new IllegalArgumentException("Invalid node type: " + node.type);
		}
	}

//	private Layout createSideMenuRoot(LayoutNode node) {
//		SideMenuLayout sideMenuLayout = new SideMenuLayout(activity);
//		for (LayoutNode child : node.children) {
//			Layout childLayout = createAndSaveToStore(child);
//			switch (child.type) {
//				case SideMenuCenter:
//					sideMenuLayout.addCenterLayout(childLayout);
//					break;
//				case SideMenuLeft:
//					sideMenuLayout.addLeftLayout(childLayout);
//					break;
//				case SideMenuRight:
//					sideMenuLayout.addRightLayout(childLayout);
//					break;
//				default:
//					throw new IllegalArgumentException("Invalid node type in sideMenu: " + node.type);
//			}
//		}
//		return sideMenuLayout;
//	}
//
//	private Layout createSideMenuContent(LayoutNode node) {
//		return createAndSaveToStore(node.children.get(0));
//	}
//
//	private Layout createSideMenuLeft(LayoutNode node) {
//		return createAndSaveToStore(node.children.get(0));
//	}
//
//	private Layout createSideMenuRight(LayoutNode node) {
//		return createAndSaveToStore(node.children.get(0));
//	}

	private ViewController createContainer(LayoutNode node) {
		return new ReactRootViewController(activity, node.id, node.data.optString("name"), reactInstanceManager);
	}

	private ViewController createContainerStack(LayoutNode node) {
		StackController stackController = new StackController(activity);
		for (LayoutNode child : node.children) {
			stackController.push(createAndSaveToStore(child));
		}
		return stackController;
	}

//	private Layout createBottomTabs(LayoutNode node) {
//		final BottomTabsLayout tabsContainer = new BottomTabsLayout(activity);
//		for (int i = 0; i < node.children.size(); i++) {
//			final Layout tabLayout = createAndSaveToStore(node.children.get(i));
//			tabsContainer.addTab("#" + i, tabLayout);
//		}
//		return tabsContainer;
//	}
}
