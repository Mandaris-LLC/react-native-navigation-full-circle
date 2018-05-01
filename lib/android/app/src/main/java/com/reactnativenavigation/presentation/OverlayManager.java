package com.reactnativenavigation.presentation;

import android.view.ViewGroup;

import com.reactnativenavigation.viewcontrollers.ViewController;

import java.util.HashMap;

public class OverlayManager {
    private final HashMap<String, ViewController> overlayRegistry = new HashMap<>();

    public void show(ViewGroup root, ViewController overlay) {
        overlayRegistry.put(overlay.getId(), overlay);
        root.addView(overlay.getView());
    }

    public void dismiss(ViewGroup root, String componentId) {
        root.removeView(overlayRegistry.get(componentId).getView());
    }

    public void destroy() {
        for (ViewController view : overlayRegistry.values()) {
            view.destroy();
        }
        overlayRegistry.clear();
    }
}
