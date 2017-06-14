package com.reactnativenavigation.utils;

import android.widget.Toast;

import com.reactnativenavigation.NavigationApplication;

public class Toaster {
	public static void toast(String text) {
		Toast.makeText(NavigationApplication.context, text, Toast.LENGTH_LONG).show();
	}
}
