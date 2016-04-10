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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guyc on 09/04/16.
 */
public class RnnToolBar extends Toolbar {

    private List<Screen> mScreens;
    private static boolean sIsSettingUpToolbar = false;

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
        if (!sIsSettingUpToolbar) {
            new SetupToolbarButtons().execute(mScreens.get(0));
        }
    }

    private static class SetupToolbarButtons extends AsyncTask<Screen, Void, Map<String, Drawable>> {
        private List<Button> mButtons;

        public SetupToolbarButtons() {
            sIsSettingUpToolbar = true;
        }

        @Override
        protected Map<String, Drawable> doInBackground(Screen... screen) {
            mButtons = screen[0].buttons;
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

            for (Button button : mButtons) {
                MenuItem item = menu.add(Menu.NONE, button.getItemId(), Menu.NONE, button.title);
                if (icons.containsKey(button.id)) {
                    Drawable icon = icons.get(button.id);

                    item.setIcon(icon).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                }
            }

            sIsSettingUpToolbar = false;
        }
    }
}
