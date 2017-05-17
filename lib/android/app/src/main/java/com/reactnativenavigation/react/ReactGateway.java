package com.reactnativenavigation.react;

import com.facebook.react.devsupport.DevSupportManager;
import com.facebook.react.devsupport.DevSupportManagerImpl;
import com.facebook.react.devsupport.ReactInstanceDevCommandsHandler;
import com.facebook.react.devsupport.RedBoxHandler;
import com.reactnativenavigation.NavigationActivity;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.utils.ReflectionUtils;

public class ReactGateway {

	public interface JsReloadListener {
		void onJsReload();
	}

	private final NavigationReactNativeHost reactNativeHost;
	private final NavigationReactInitializer initializer;
	private final JsDevReloadHandler jsDevReloadHandler;

	public ReactGateway(final NavigationApplication application, final boolean isDebug) {
		reactNativeHost = new NavigationReactNativeHost(application, isDebug);

		DevSupportManager devSupportManager = reactNativeHost.getReactInstanceManager().getDevSupportManager();
		ReactInstanceDevCommandsHandler reactInstanceCommandsHandler = (ReactInstanceDevCommandsHandler) ReflectionUtils.getDeclaredField(devSupportManager, "mReactInstanceCommandsHandler");
		String packagerPathForJsBundle = reactNativeHost.getJSMainModuleName();
		boolean enableOnCreate = reactNativeHost.getUseDeveloperSupport();
		RedBoxHandler redBoxHandler = reactNativeHost.getRedBoxHandler();
		DevSupportManagerImpl proxy = new DevSupportManagerImpl(application, reactInstanceCommandsHandler, packagerPathForJsBundle, enableOnCreate, redBoxHandler) {
			@Override
			public void handleReloadJS() {
				super.handleReloadJS();
				// onJsReload
//				ReactContext currentReactContext = reactNativeHost.getReactInstanceManager().getCurrentReactContext();
//				((NavigationActivity) currentReactContext.getCurrentActivity()).getNavigator().onDestroy();
			}
		};
		ReflectionUtils.setField(reactNativeHost.getReactInstanceManager(), "mDevSupportManager", proxy);

		initializer = new NavigationReactInitializer(reactNativeHost.getReactInstanceManager(), isDebug);
		jsDevReloadHandler = new JsDevReloadHandler(reactNativeHost.getReactInstanceManager());
	}

	public NavigationReactNativeHost getReactNativeHost() {
		return reactNativeHost;
	}

	public void onActivityCreated(NavigationActivity activity) {
		initializer.onActivityCreated(activity);
	}

	public void onActivityResumed(NavigationActivity activity) {
		initializer.onActivityResumed(activity);
	}

	public void onActivityPaused(NavigationActivity activity) {
		initializer.onActivityPaused(activity);
	}

	public void onActivityDestroyed(NavigationActivity activity) {
		initializer.onActivityDestroyed(activity);
	}

	public boolean onKeyUp(final int keyCode) {
		return jsDevReloadHandler.onKeyUp(keyCode);
	}
}
