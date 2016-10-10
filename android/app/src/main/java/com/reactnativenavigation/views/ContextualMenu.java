package com.reactnativenavigation.views;

import android.content.Context;
import android.view.Menu;

import com.facebook.react.bridge.Callback;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.events.ContextualMenuHiddenEvent;
import com.reactnativenavigation.events.EventBus;
import com.reactnativenavigation.params.ContextualMenuButtonParams;
import com.reactnativenavigation.params.ContextualMenuParams;
import com.reactnativenavigation.params.StyleParams;
import com.reactnativenavigation.params.TitleBarLeftButtonParams;

import java.util.List;

public class ContextualMenu extends TitleBar implements LeftButtonOnClickListener, ContextualMenuButton.ContextualButtonClickListener {
    private Callback onButtonClicked;
    private final String navigatorEventId;

    public ContextualMenu(Context context, ContextualMenuParams params, StyleParams.Color contextualMenuBackgroundColor, Callback onButtonClicked) {
        super(context);
        this.onButtonClicked = onButtonClicked;
        navigatorEventId = params.navigationParams.navigatorEventId;
        setStyle(contextualMenuBackgroundColor);
        setButtons(params.buttons, params.leftButton);
    }

    public void setStyle(StyleParams.Color contextualMenuBackgroundColor) {
        if (contextualMenuBackgroundColor.hasColor()) {
            setBackgroundColor(contextualMenuBackgroundColor.getColor());
        }
    }

    public void setButtons(List<ContextualMenuButtonParams> buttons, TitleBarLeftButtonParams leftButton) {
        addButtonsToContextualMenu(buttons, getMenu());
        setBackButton(leftButton);
    }

    private void setBackButton(TitleBarLeftButtonParams leftButton) {
        setLeftButton(leftButton, this, null, false);
    }

    private void addButtonsToContextualMenu(List<ContextualMenuButtonParams> buttons, Menu menu) {
        for (int i = 0; i < buttons.size(); i++) {
            final TitleBarButton button = new ContextualMenuButton(menu, this, buttons.get(i), this);
            addButtonInReverseOrder(buttons, i, button);
        }
    }

    @Override
    public boolean onTitleBarBackButtonClick() {
        dismiss();
        EventBus.instance.post(new ContextualMenuHiddenEvent());
        return true;
    }

    @Override
    public void onSideMenuButtonClick() {
        // nothing
    }

    @Override
    public void onClick(int index) {
        dismiss();
        EventBus.instance.post(new ContextualMenuHiddenEvent());
        onButtonClicked.invoke(index);
    }

    public void dismiss() {
        hide();
        NavigationApplication.instance.sendNavigatorEvent("contextualMenuDismissed", navigatorEventId);
    }
}
