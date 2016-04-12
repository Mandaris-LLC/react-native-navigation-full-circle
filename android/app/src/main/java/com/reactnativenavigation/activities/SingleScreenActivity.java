package com.reactnativenavigation.activities;

import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.reactnativenavigation.R;
import com.reactnativenavigation.core.RctManager;
import com.reactnativenavigation.core.objects.Screen;
import com.reactnativenavigation.views.ScreenStack;

/**
 * Created by guyc on 05/04/16.
 */
public class SingleScreenActivity extends BaseReactActivity {

    public static final String EXTRA_SCREEN = "extraScreen";

    private Toolbar mToolbar;
    private FrameLayout mContentFrame;
    private ScreenStack screenStack;
    private String navID;

    @Override
    protected void handleOnCreate() {
        mReactInstanceManager = RctManager.getInstance().getReactInstanceManager();

        setContentView(R.layout.single_screen_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mContentFrame = (FrameLayout) findViewById(R.id.contentFrame);

        Screen screen = (Screen) getIntent().getSerializableExtra(EXTRA_SCREEN);
        navID = screen.navigatorId;
        setupToolbar(screen.title);

        screenStack = new ScreenStack(this);
        mContentFrame.addView(screenStack);
        screenStack.push(screen);
    }

    private void setupToolbar(String title) {
        mToolbar.setTitle(title);
    }
    
    @Override
    public void push(Screen screen) {
        screenStack.push(screen);
    }

    @Override
    public Screen pop(String navID) {
        return screenStack.pop();
    }

    @Override
    public String getActiveNavigatorID() {
        return navID;
    }
}
