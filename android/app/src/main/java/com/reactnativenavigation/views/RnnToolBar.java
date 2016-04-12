package com.reactnativenavigation.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;

import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.core.objects.Button;
import com.reactnativenavigation.core.objects.Screen;
import com.reactnativenavigation.utils.ContextProvider;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guyc on 09/04/16.
 */
public class RnnToolBar extends Toolbar {

    private List<Screen> mScreens;
    private AsyncTask mSetupToolbarTask;

    public RnnToolBar(Context context) {
        super(context);
    }

    public RnnToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RnnToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScreens(List<Screen> screens) {
        mScreens = screens;
    }

    public void handleOnCreateOptionsMenuAsync() {
        setupToolbarButtonsAsync(mScreens.get(0));
    }

    public void setupToolbarButtonsAsync(Screen screen) {
        if (mSetupToolbarTask == null) {
            mSetupToolbarTask = new SetupToolbarButtonsTask(this, screen).execute();
        }
    }

    private static class SetupToolbarButtonsTask extends AsyncTask<Void, Void, Map<String, Drawable>> {
        private final List<Button> mButtons;
        private final WeakReference<RnnToolBar> mToolbarWR;

        public SetupToolbarButtonsTask(RnnToolBar toolBar, Screen newScreen) {
            mToolbarWR = new WeakReference<>(toolBar);
            mButtons = newScreen.buttons == null ? Collections.EMPTY_LIST : newScreen.buttons;
        }

        @Override
        protected Map<String, Drawable> doInBackground(Void... params) {
            Context context = ContextProvider.getActivityContext();
            if (context == null) {
                return null;
            }

            Map<String, Drawable> icons = new HashMap<>();
            for (Button button : mButtons) {
                if (button.hasIcon()) {
                    icons.put(button.id, button.getIcon(context));
                }
            }
            return icons;
        }

        @Override
        protected void onPostExecute(Map<String, Drawable> icons) {
            Context context = ContextProvider.getActivityContext();
            if (context == null) {
                return;
            }

            Menu menu = ((BaseReactActivity) context).getMenu();

            // Add new screen buttons
            int i;
            for (i = 0; i < mButtons.size(); i++) {
                Button button = mButtons.get(i);
                MenuItem item = menu.add(Menu.NONE, button.getItemId(), i, button.title);
                if (icons.containsKey(button.id)) {
                    Drawable icon = icons.get(button.id);
                    item.setIcon(icon).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                }
            }

            // Remove prev screen buttons
            for (int j = i; j < menu.size(); j++) {
                menu.removeItem(j);
            }

            RnnToolBar toolBar = mToolbarWR.get();
            if (toolBar != null) {
                toolBar.mSetupToolbarTask = null;
                mToolbarWR.clear();
            }
        }
    }
}
