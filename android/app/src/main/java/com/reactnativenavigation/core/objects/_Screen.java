package com.reactnativenavigation.core.objects;

/**
 * Created by guyc on 02/04/16.
 */
public class _Screen {
//    private static final long serialVersionUID = -1033475305421107791L;
//
//    private static final String KEY_TITLE = "title";
//    private static final String KEY_SCREEN = "screen";
//    private static final String KEY_LABEL = "tabLabel";
//    public static final String KEY_SCREEN_INSTANCE_ID = "screenInstanceID";
//    public static final String KEY_NAVIGATOR_ID = "navigatorID";
//    public static final String KEY_NAVIGATOR_EVENT_ID = "navigatorEventID";
//    private static final String KEY_ICON = "tabIcon";
//    private static final String KEY_NAVIGATOR_BUTTONS = "navigatorButtons";
//    private static final String KEY_RIGHT_BUTTONS = "rightButtons";
//    private static final String KEY_TOOL_BAR_STYLE = "navigatorStyle";
//    private static final String KEY_STATUS_BAR_COLOR = "statusBarColor";
//    private static final String KEY_TOOL_BAR_COLOR = "navBarBackgroundColor";
//    private static final String KEY_TOOL_BAR_HIDDEN = "navBarHidden";
//    private static final String KEY_NAVIGATION_BAR_COLOR = "navigationBarColor";
//    private static final String KEY_NAV_BAR_BUTTON_COLOR = "navBarButtonColor";
//    private static final String KEY_NAV_BAR_TEXT_COLOR = "navBarTextColor";
//    private static final String KEY_BACK_BUTTON_HIDDEN = "backButtonHidden";
//    private static final String KEY_TAB_NORMAL_TEXT_COLOR = "tabNormalTextColor";
//    private static final String KEY_TAB_SELECTED_TEXT_COLOR = "tabSelectedTextColor";
//    private static final String KEY_TAB_INDICATOR_COLOR = "tabIndicatorColor";
//    private static final String KEY_BOTTOM_TABS_HIDDEN = "tabBarHidden";
//    private static final String KEY_BOTTOM_TABS_HIDDEN_ON_SCROLL = "bottomTabsHiddenOnScroll";
//    private static final String KEY_PROPS = "passProps";
//
//    public String title;
//    public final String tabLabel;
//    public final String screenId;
//    public final String screenInstanceId;
//    public final String navigatorId;
//    public final String navigatorEventId;
//    public final String tabIcon;
//    public ArrayList<_Button> buttons;
//    public final boolean backButtonHidden;
//    public boolean bottomTabsHiddenOnScroll;
//    public HashMap<String, Object> passedProps = new HashMap<>();
//
//    // NavigationReactModule styling
//    @Nullable @ColorInt public Integer toolBarColor;
//    @Nullable public Boolean toolBarHidden;
//    @Nullable @ColorInt public Integer statusBarColor;
//    @Nullable @ColorInt public Integer navigationBarColor;
//    @Nullable @ColorInt public Integer navBarButtonColor;
//    @Nullable @ColorInt public Integer navBarTextColor;
//    @Nullable @ColorInt public Integer tabNormalTextColor;
//    @Nullable @ColorInt public Integer tabSelectedTextColor;
//    @Nullable @ColorInt public Integer tabIndicatorColor;
//    public Boolean bottomTabsHidden;
//
//    @NonNull
//    public List<_Button> getButtons() {
//        return buttons == null ? Collections.<_Button>emptyList() : buttons;
//    }
//
//    public _Screen(ReadableMap screen) {
//        title = getString(screen, KEY_TITLE);
//        tabLabel = getString(screen, KEY_LABEL);
//        screenId = getString(screen, KEY_SCREEN);
//        screenInstanceId = getString(screen, KEY_SCREEN_INSTANCE_ID);
//        navigatorId = getString(screen, KEY_NAVIGATOR_ID);
//        navigatorEventId = getString(screen, KEY_NAVIGATOR_EVENT_ID);
//        tabIcon = getString(screen, KEY_ICON);
//        if(screen.hasKey(KEY_PROPS)) {
//            passedProps = ((ReadableNativeMap) screen.getMap(KEY_PROPS)).toHashMap();
//        }
//        buttons = getButtons(screen);
//        backButtonHidden = getBoolean(screen, KEY_BACK_BUTTON_HIDDEN);
//        setToolbarStyle(screen);
//    }
//
//    public void setTitle(ReadableMap params) {
//        this.title = getString(params, KEY_TITLE);
//    }
//
//    public void setButtons(ReadableMap params) {
//        this.buttons = getButtons(params);
//    }
//
//    private ArrayList<_Button> getButtons(ReadableMap screen) {
//        ArrayList<_Button> ret = new ArrayList<>();
//        if (hasButtons(screen)) {
//            ReadableArray rightButtons = getRightButtons(screen);
//            for (int i = 0; i < rightButtons.size(); i++) {
//                ret.add(new _Button(rightButtons.getMap(i)));
//            }
//        }
//        return ret;
//    }
//
//    private boolean hasButtons(ReadableMap screen) {
//        return screen.hasKey(KEY_RIGHT_BUTTONS) || screen.hasKey(KEY_NAVIGATOR_BUTTONS);
//    }
//
//    private ReadableArray getRightButtons(ReadableMap screen) {
//        return screen.hasKey(KEY_RIGHT_BUTTONS) ? screen.getArray(KEY_RIGHT_BUTTONS) :
//                screen.getMap(KEY_NAVIGATOR_BUTTONS).getArray(KEY_RIGHT_BUTTONS);
//    }
//
//    public Drawable getIcon(Context ctx) {
//        return ImageLoader.getIcon(ctx, tabIcon);
//    }
//
//    public void setToolbarStyle(ReadableMap screen) {
//        ReadableMap style = getMap(screen, KEY_TOOL_BAR_STYLE);
//        if (style != null) {
//            toolBarColor = getColor(style, KEY_TOOL_BAR_COLOR);
//            toolBarHidden = getBoolean(style, KEY_TOOL_BAR_HIDDEN);
//            statusBarColor = getColor(style, KEY_STATUS_BAR_COLOR);
//            navigationBarColor = getColor(style, KEY_NAVIGATION_BAR_COLOR);
//            navBarButtonColor = getColor(style, KEY_NAV_BAR_BUTTON_COLOR);
//            navBarTextColor = getColor(style, KEY_NAV_BAR_TEXT_COLOR);
//            tabNormalTextColor = getColor(style, KEY_TAB_NORMAL_TEXT_COLOR);
//            tabSelectedTextColor = getColor(style, KEY_TAB_SELECTED_TEXT_COLOR);
//            tabIndicatorColor = getColor(style, KEY_TAB_INDICATOR_COLOR);
//            bottomTabsHidden = getBoolean(style, KEY_BOTTOM_TABS_HIDDEN);
//            bottomTabsHiddenOnScroll = getBoolean(style, KEY_BOTTOM_TABS_HIDDEN_ON_SCROLL);
//        }
//    }
}
