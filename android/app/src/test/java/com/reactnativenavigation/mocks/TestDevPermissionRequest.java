package com.reactnativenavigation.mocks;

import com.reactnativenavigation.react.DevPermissionRequest;

public class TestDevPermissionRequest implements DevPermissionRequest {
	@Override
	public boolean shouldAskPermission() {
		return false;
	}

	@Override
	public void askPermission() {
		// nothing
	}
}
