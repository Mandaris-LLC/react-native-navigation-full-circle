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
    private ScreenStack mScreenStack;
    private String mNavigatorId;

    @Override
    protected void handleOnCreate() {
        mReactInstanceManager = RctManager.getInstance().getReactInstanceManager();

        setContentView(R.layout.single_screen_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        Screen screen = (Screen) getIntent().getSerializableExtra(EXTRA_SCREEN);
        mNavigatorId = screen.navigatorId;
        setupToolbar(screen.title);

        mScreenStack = new ScreenStack(this);
        FrameLayout contentFrame = (FrameLayout) findViewById(R.id.contentFrame);
        contentFrame.addView(mScreenStack);
        mScreenStack.push(screen);
    }

    private void setupToolbar(String title) {
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
    }
    
    @Override
    public void push(Screen screen) {
        super.push(screen);
        mScreenStack.push(screen);
    }

    @Override
    public Screen pop(String navigatorId) {
        super.pop(navigatorId);
        return mScreenStack.pop();
    }

    @Override
    public String getCurrentNavigatorId() {
        return mNavigatorId;
    }

    @Override
    protected Screen getCurrentScreen() {
        return mScreenStack.peek();
    }

    @Override
    public int getScreenStackSize() {
        return mScreenStack.getStackSize();
    }
}
