package com.reactnativenavigation.core.objects;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guyc on 02/04/16.
 */
public class Screen extends JsonObject implements Serializable {
    private static final long serialVersionUID = -1033475305421107791L;

    private static final String KEY_TITLE = "title";
    private static final String KEY_SCREEN = "screen";
    public static final String KEY_SCREEN_INSTANCE_ID = "screenInstanceID";
    public static final String KEY_STACK_ID = "stackID";
    public static final String KEY_NAVIGATOR_ID = "navigatorID";
    public static final String KEY_NAVIGATOR_EVENT_ID = "navigatorEventID";
    private static final String KEY_ICON = "icon";
    private static final String KEY_RIGHT_BUTTONS = "rightButtons";

    public String title;
    public String screenId;
    public String screenInstanceId;
    public String stackId;
    public String navigatorId;
    public String navigatorEventId;
    public int icon;
    public List<Button> buttons;

    public Screen(ReadableMap screen) {
        title = getString(screen, KEY_TITLE);
        screenId = getString(screen, KEY_SCREEN);
        screenInstanceId = getString(screen, KEY_SCREEN_INSTANCE_ID);
        stackId = getString(screen, KEY_STACK_ID);
        navigatorId = getString(screen, KEY_NAVIGATOR_ID);
        navigatorEventId = getString(screen, KEY_NAVIGATOR_EVENT_ID);
        icon = getInt(screen, KEY_ICON);

        if (screen.hasKey(KEY_RIGHT_BUTTONS)) {
            buttons = new ArrayList<>();
            ReadableArray rightButtons = screen.getArray(KEY_RIGHT_BUTTONS);
            for (int i = 0; i < rightButtons.size(); i++) {
                buttons.add(new Button(rightButtons.getMap(i)));
            }
        }
    }
}
