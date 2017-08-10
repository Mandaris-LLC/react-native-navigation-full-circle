package com.reactnativenavigation.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.reactnativenavigation.params.NavigationParams;
import com.reactnativenavigation.params.TitleBarButtonParams;

class TitleBarCustomComponentButton extends TitleBarButton {
    TitleBarCustomComponentButton(Menu menu, View parent, TitleBarButtonParams buttonParams, @Nullable String navigatorEventId) {
        super(menu, parent, buttonParams, navigatorEventId);
    }

    @Override
    MenuItem addToMenu(int index) {
        throw new RuntimeException("Can't add custom component to menu");
    }

    View createView(Context context) {
        return new ContentView(context, buttonParams.componentName, NavigationParams.EMPTY, buttonParams.componentProps);
    }
}
