package com.reactnativenavigation.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.reactnativenavigation.R;
import com.reactnativenavigation.core.RctManager;
import com.reactnativenavigation.core.objects.Screen;
import com.reactnativenavigation.views.RctView;

/**
 * Created by guyc on 05/04/16.
 */
public class SingleScreenActivity extends BaseReactActivity {

    public static final String EXTRA_SCREEN = "extraScreen";

    private Toolbar mToolbar;
    private FrameLayout mContentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("GUY", "onCreate SingleScreenActivity");
    }

    @Override
    protected void handleOnCreate() {
        mReactInstanceManager = RctManager.getInstance().getReactInstanceManager();

        setContentView(R.layout.single_screen_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mContentFrame = (FrameLayout) findViewById(R.id.contentFrame);

        Screen screen = (Screen) getIntent().getSerializableExtra(EXTRA_SCREEN);
        setupToolbar(screen.title);
        setupReactView(screen);
    }

    private void setupToolbar(String title) {
        mToolbar.setTitle(title);
    }

    private void setupReactView(Screen screen) {
        View view = new RctView(this, mReactInstanceManager, screen);
        mContentFrame.addView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("GUY", "onResume SingleScreenActivity");
    }
}
