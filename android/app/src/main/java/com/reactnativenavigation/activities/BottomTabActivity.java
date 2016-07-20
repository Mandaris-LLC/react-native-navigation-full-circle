package com.reactnativenavigation.activities;

public class BottomTabActivity  {
//    private static final String TAG = "BottomTabActivity";
//    public static final String DRAWER_PARAMS = "drawerParams";
//    public static final String EXTRA_SCREENS = "extraScreens";
//
//    private static final String TAB_STYLE_BUTTON_COLOR = "tabBarButtonColor";
//    private static final String TAB_STYLE_SELECTED_COLOR = "tabBarSelectedButtonColor";
//    private static final String TAB_STYLE_BAR_BG_COLOR = "tabBarBackgroundColor";
//    private static final String TAB_STYLE_INACTIVE_TITLES = "tabShowInactiveTitles";
//
//    private static int DEFAULT_TAB_BAR_BG_COLOR = 0xFFFFFFFF;
//    private static int DEFAULT_TAB_BUTTON_COLOR = Color.GRAY;
//    private static int DEFAULT_TAB_SELECTED_COLOR = 0xFF0000FF;
//    private static boolean DEFAULT_TAB_INACTIVE_TITLES = true;
//
//    private BottomNavigation mBottomNavigation;
//    private CoordinatorLayout mContentFrame;
//    private ArrayList<ScreenStack> mScreenStacks;
//    private int mCurrentStackPosition = -1;
//
//    @Override
//    protected void handleOnCreate() {
//        super.handleOnCreate();
//        reactInstanceManager = RctManager.getInstance().getReactInstanceManager();
//
//        setContentView(R.layout.bottom_tab_activity);
//        mToolbar = (RnnToolBar) findViewById(R.id.toolbar);
//        mBottomNavigation = (BottomNavigation) findViewById(R.id.bottom_tab_bar);
//        mContentFrame = (CoordinatorLayout) findViewById(R.id.contentFrame);
//
//        final ArrayList<_Screen> screens = (ArrayList<_Screen>) getIntent().getSerializableExtra(EXTRA_SCREENS);
//        final Drawer drawer = (Drawer) getIntent().getSerializableExtra(DRAWER_PARAMS);
//        mBottomNavigation.setForceTint(true);
//        setupDrawer(screens.get(0), drawer, R.id.drawerFrame, R.id.drawerLayout);
//        setupTabs(getIntent().getExtras());
//        setupPages(screens);
//
//        // Setup Toolbar after it's measured since tabIcon height is dependent on Toolbar height
//        mContentFrame.post(new Runnable() {
//            @Override
//            public void run() {
//                setupToolbar(screens);
//            }
//        });
//    }
//
//    private void setupPages(ArrayList<_Screen> screens) {
//        new SetupTabsTask(this, toolbar, screens).execute();
//    }
//
//    private void setupToolbar(ArrayList<_Screen> screens) {
//        toolbar.setScreens(screens);
//        _Screen initialScreen = screens.get(0);
//        toolbar.update(initialScreen);
//        StyleHelper.updateStyles(toolbar, initialScreen);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mScreenStacks != null) {
//            StyleHelper.updateStyles(toolbar, getCurrentScreen());
//        }
//    }
//
//    private void setupTabs(Bundle style) {
//        mBottomNavigation.setForceTitlesDisplay(style.getBoolean(TAB_STYLE_INACTIVE_TITLES, DEFAULT_TAB_INACTIVE_TITLES));
//        mBottomNavigation.setForceTint(true);
//        mBottomNavigation.setDefaultBackgroundColor(getColor(style, TAB_STYLE_BAR_BG_COLOR, DEFAULT_TAB_BAR_BG_COLOR));
//        mBottomNavigation.setInactiveColor(getColor(style, TAB_STYLE_BUTTON_COLOR, DEFAULT_TAB_BUTTON_COLOR));
//        mBottomNavigation.setAccentColor(getColor(style, TAB_STYLE_SELECTED_COLOR, DEFAULT_TAB_SELECTED_COLOR));
//    }
//
//    private static int getColor(Bundle bundle, String key, int defaultColor) {
//        if (bundle.containsKey(key)) {
//            return Color.parseColor(bundle.getString(key));
//        } else {
//            return defaultColor;
//        }
//    }
//
//    @Override
//    public void push(_Screen screen) {
//        super.push(screen);
//        for (ScreenStack stack : mScreenStacks) {
//            if (stack.peek().navigatorId.equals(screen.navigatorId)) {
//                stack.push(screen);
//            }
//        }
//        StyleHelper.updateStyles(toolbar, getCurrentScreen());
//
//        if (shouldToggleTabs(screen)) {
//            mBottomNavigation.toggleTabs(screen.bottomTabsHidden, false);
//        }
//    }
//
//    @Override
//    public _Screen pop(String navigatorId) {
//        super.pop(navigatorId);
//        for (ScreenStack stack : mScreenStacks) {
//            if (stack.peek().navigatorId.equals(navigatorId)) {
//                _Screen popped = stack.pop();
//                _Screen currentScreen = getCurrentScreen();
//                StyleHelper.updateStyles(toolbar, currentScreen);
//
//                if (shouldToggleTabs(currentScreen)) {
//                    mBottomNavigation.toggleTabs(currentScreen.bottomTabsHidden, false);
//                }
//
//                return popped;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public _Screen popToRoot(String navigatorId) {
//        super.popToRoot(navigatorId);
//        for (ScreenStack stack : mScreenStacks) {
//            if (stack.peek().navigatorId.equals(navigatorId)) {
//                _Screen popped = stack.popToRoot();
//                _Screen currentScreen = getCurrentScreen();
//                StyleHelper.updateStyles(toolbar, currentScreen);
//
//                if (shouldToggleTabs(currentScreen)) {
//                    mBottomNavigation.toggleTabs(currentScreen.bottomTabsHidden, false);
//                }
//
//                return popped;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public _Screen getCurrentScreen() {
//        _Screen currentScreen = super.getCurrentScreen();
//        if (currentScreen != null) {
//            return currentScreen;
//        }
//
//        return mScreenStacks != null ? mScreenStacks.get(mCurrentStackPosition).peek() : null;
//    }
//
//    public _Screen resetTo(_Screen screen) {
//        super.resetTo(screen);
//        StyleHelper.updateStyles(toolbar, screen);
//        return mScreenStacks.get(mCurrentStackPosition).resetTo(screen);
//    }
//
//    @Override
//    protected String getCurrentNavigatorId() {
//        return mScreenStacks.get(mCurrentStackPosition).peek().navigatorId;
//    }
//
//    @Override
//    public int getScreenStackSize() {
//        return mScreenStacks.get(mCurrentStackPosition).getStackSize();
//    }
//
//    @Override
//    public void onTabSelected(int position, boolean wasSelected) {
//        if (wasSelected) {
//            return;
//        }
//
//        // Remove current ScreenStack
//        if (mCurrentStackPosition >= 0) {
//            mScreenStacks.get(mCurrentStackPosition).removeFromScreen(mContentFrame);
//        }
//
//        // Add new ScreenStack
//        mScreenStacks.get(position).addToScreen(mContentFrame);
//
//        mCurrentStackPosition = position;
//        StyleHelper.updateStyles(toolbar, getCurrentScreen());
//
//        // Hide or show back button if needed
//        if (getScreenStackSize() > 1) {
//            toolbar.setNavUpButton(getCurrentScreen());
//        } else {
//            toolbar.setNavUpButton();
//        }
//    }
//
//    public void setTabBadge(ReadableMap params) {
//        // Badge comes across as int, but if it's 0 clear the notification
//        int badgeCount = params.getInt(KEY_BADGE);
//        String badge = (badgeCount > 0) ? Integer.toString(badgeCount) : "";
//
//        // Tab index is optional, so default to current tab
//        int tabIndex = mBottomNavigation.getCurrentItem();
//        if (params.hasKey(KEY_TAB_INDEX)) {
//            tabIndex = params.getInt(KEY_TAB_INDEX);
//        }
//
//        mBottomNavigation.setNotification(badge, tabIndex);
//    }
//
//    public void switchToTab(ReadableMap params) {
//        Integer tabIndex;
//        if (params.hasKey(KEY_TAB_INDEX)) {
//            tabIndex = params.getInt(KEY_TAB_INDEX);
//        } else {
//            final String navigatorId = params.getString(KEY_NAVIGATOR_ID);
//            tabIndex = findNavigatorTabIndex(navigatorId);
//        }
//        mBottomNavigation.setCurrentItem(tabIndex);
//    }
//
//    public void toggleTabs(ReadableMap params) {
//        boolean hide = params.getBoolean(KEY_HIDDEN);
//        boolean animated = params.getBoolean(KEY_ANIMATED);
//        mBottomNavigation.toggleTabs(hide, animated);
//    }
//
//    private boolean shouldToggleTabs(_Screen newScreen) {
//        return mBottomNavigation.isShown() == newScreen.bottomTabsHidden;
//    }
//
//    public void onScrollChanged(int direction) {
//        mBottomNavigation.onScroll(direction);
//    }
//
//    private static class SetupTabsTask extends AsyncTask<Void, Void, Map<_Screen, Drawable>> {
//        private BottomTabActivity mActivity;
//        private RnnToolBar mToolBar;
//        private ArrayList<_Screen> mScreens;
//
//        public SetupTabsTask(BottomTabActivity context, RnnToolBar toolBar, ArrayList<_Screen> screens) {
//            mActivity = context;
//            mToolBar = toolBar;
//            mScreens = screens;
//        }
//
//        @Override
//        protected Map<_Screen, Drawable> doInBackground(Void... params) {
//            Map<_Screen, Drawable> icons = new HashMap<>();
//            for (_Screen screen : mScreens) {
//                if (screen.tabIcon != null) {
//                    icons.put(screen, screen.getIcon(this.mActivity));
//                }
//            }
//            return icons;
//        }
//
//        @Override
//        protected void onPostExecute(Map<_Screen, Drawable> icons) {
//            mActivity.setTabsWithIcons(mScreens, icons);
//            StyleHelper.updateStyles(mToolBar, mActivity.getCurrentScreen());
//        }
//    }
//
//    protected Integer findNavigatorTabIndex(String navigatorId) {
//        for (int i = 0; i < mScreenStacks.size(); i++) {
//            ScreenStack stack = mScreenStacks.get(i);
//            if (!stack.isEmpty() && stack.peek().navigatorId.equals(navigatorId)) {
//                return i;
//            }
//        }
//
//        return null;
//    }
//
//    private void setTabsWithIcons(ArrayList<_Screen> screens, Map<_Screen, Drawable> icons) {
//        mScreenStacks = new ArrayList<>();
//        for (int i = 0; i < screens.size(); i++) {
//            final _Screen screen = screens.get(i);
//            ScreenStack stack = new ScreenStack(this);
//            stack.push(screen);
//            mScreenStacks.add(stack);
//            AHBottomNavigationItem item = new AHBottomNavigationItem(screen.tabLabel, icons.get(screen), Color.GRAY);
//            mBottomNavigation.addItem(item);
//            mBottomNavigation.setOnTabSelectedListener(this);
//        }
//        this.onTabSelected(0, false);
//    }
//
//    @Override
//    protected void removeAllReactViews() {
//        for (ScreenStack screenStack : mScreenStacks) {
//            screenStack.removeAllReactViews();
//        }
//    }
}
