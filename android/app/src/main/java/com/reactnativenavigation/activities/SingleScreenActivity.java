package com.reactnativenavigation.activities;

public class SingleScreenActivity {
//
//    public static final String DRAWER_PARAMS = "drawerParams";
//    public static final String EXTRA_SCREEN = "extraScreen";
//
//    private ScreenStack mScreenStack;
//    private String mNavigatorId;
//
//    @Override
//    protected void handleOnCreate() {
//        super.handleOnCreate();
//        reactInstanceManager = RctManager.getInstance().getReactInstanceManager();
//
//        setContentView(R.layout.single_screen_activity);
//        toolbar = (RnnToolBar) findViewById(R.id.toolbar);
//
//        final _Screen screen = (_Screen) getIntent().getSerializableExtra(EXTRA_SCREEN);
//        final Drawer drawer = (Drawer) getIntent().getSerializableExtra(DRAWER_PARAMS);
//
//        mNavigatorId = screen.navigatorId;
//        setupToolbar(screen);
//        setupDrawer(screen, drawer, R.id.drawerFrame, R.id.drawerLayout);
//
//        mScreenStack = new ScreenStack(this);
//        CoordinatorLayout contentFrame = (CoordinatorLayout) findViewById(R.id.contentFrame);
//        assert contentFrame != null;
//        contentFrame.addView(mScreenStack);
//        mScreenStack.push(screen);
//
//        // Setup Toolbar after it's measured since icon height is dependent on Toolbar height
//        contentFrame.post(new Runnable() {
//            @Override
//            public void run() {
//                setupToolbar(screen);
//            }
//        });
//    }
//
//    protected void setupToolbar(_Screen screen) {
//        StyleHelper.updateStyles(toolbar, screen);
//    }
//
//    @Override
//    public void push(_Screen screen) {
//        super.push(screen);
//        mScreenStack.push(screen);
//        StyleHelper.updateStyles(toolbar, screen);
//    }
//
//    @Override
//    public _Screen pop(String navigatorId) {
//        super.pop(navigatorId);
//        _Screen popped = mScreenStack.pop();
//        StyleHelper.updateStyles(toolbar, getCurrentScreen());
//        return popped;
//    }
//
//    @Override
//    public _Screen popToRoot(String navigatorId) {
//        super.popToRoot(navigatorId);
//        _Screen screen = mScreenStack.popToRoot();
//        StyleHelper.updateStyles(toolbar, getCurrentScreen());
//        return screen;
//    }
//
//    @Override
//    public _Screen resetTo(_Screen screen) {
//        super.resetTo(screen);
//        _Screen popped = mScreenStack.resetTo(screen);
//        StyleHelper.updateStyles(toolbar, screen);
//        return popped;
//    }
//
//    @Override
//    public String getCurrentNavigatorId() {
//        return mNavigatorId;
//    }
//
//    @Override
//    public _Screen getCurrentScreen() {
//        _Screen currentScreen = super.getCurrentScreen();
//        if (currentScreen != null) {
//            return currentScreen;
//        }
//
//        return mScreenStack.peek();
//    }
//
//    @Override
//    public int getScreenStackSize() {
//        return mScreenStack.getStackSize();
//    }
//
//    @Override
//    protected void removeAllReactViews() {
//        mScreenStack.removeAllReactViews();
//    }
//
//    @Override
//    public void showFAB(ReadableMap params) {
////        FloatingActionButton fab = new FloatingActionButton(this);
////        fab.setImageDrawable(IconUtils.getIcon(this, params.getString("icon")));
////        fab.setBackgroundColor(Color.parseColor(params.getString("backgroundColor")));
////        fab.setImageResource(R.drawable.notification_background);
////        CoordinatorLayout content = (CoordinatorLayout) findViewById(R.id.contentFrame);
////        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
////        layoutParams.gravity = Gravity.BOTTOM | Gravity.END;
////        int m = (int) ImageUtils.convertDpToPixel(16, this);
////        layoutParams.setMargins(m, m, m, m);
////        content.addView(fab, layoutParams);
//    }
}
