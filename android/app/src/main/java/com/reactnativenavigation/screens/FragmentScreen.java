package com.reactnativenavigation.screens;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.reactnativenavigation.params.ScreenParams;
import com.reactnativenavigation.utils.ViewUtils;
import com.reactnativenavigation.views.ContentView;
import com.reactnativenavigation.views.TitleBarBackButtonListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

@SuppressWarnings("ResourceType")
public class FragmentScreen extends Screen {

    private static final String CONTRACT_GET_FRAGMENT = "getFragment";
    private static final String CONTRACT_GET_SUPPORT_FRAGMENT = "getSupportFragment";
    private FrameLayout content;

    public FragmentScreen(AppCompatActivity activity, ScreenParams screenParams, TitleBarBackButtonListener titleBarBackButtonListener) {
        super(activity, screenParams, titleBarBackButtonListener);
    }

    @Override
    protected void createContent() {
        content = new FrameLayout(getContext());
        content.setId(ViewUtils.generateViewId());
        addContent();
        addFragment();
    }

    private void addContent() {
        ContentView contentView = new ContentView(getContext(), screenParams.screenId, screenParams.passProps, screenParams.navigationParams, null);
        addView(contentView, 0, 0);
        LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        if (screenParams.styleParams.drawBelowTopBar) {
            params.addRule(BELOW, topBar.getId());
        }
        addView(content, params);
    }


    private void addFragment() {
        try {
            Fragment fragment = tryGetFragment();
            if (fragment != null) {
                addFragment(fragment);
                return;
            }

            android.support.v4.app.Fragment supportFragment = tryGetSupportFragment();
            if (supportFragment != null) {
                addSupportFragment(supportFragment);
                return;
            }
            throw new RuntimeException("must provide public static method " + CONTRACT_GET_FRAGMENT + " or " + CONTRACT_GET_SUPPORT_FRAGMENT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(content.getId(), fragment);
        transaction.commit();
    }

    private void addSupportFragment(android.support.v4.app.Fragment supportFragment) {
        android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(content.getId(), supportFragment);
        transaction.commit();
    }

    @Nullable
    private Fragment tryGetFragment() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException {
        try {
            String className = screenParams.fragmentCreatorClassName;
            Class<?> fragmentCreatorClass = Class.forName(className);
            Method method = fragmentCreatorClass.getMethod(CONTRACT_GET_FRAGMENT, Bundle.class);
            return (android.app.Fragment) method.invoke(null, screenParams.passProps);
        } catch (NoSuchMethodException noSuchMethod) {
            return null;
        }
    }

    @Nullable
    private android.support.v4.app.Fragment tryGetSupportFragment() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException {
        try {
            String className = screenParams.fragmentCreatorClassName;
            Class<?> fragmentCreatorClass = Class.forName(className);
            Method method = fragmentCreatorClass.getMethod(CONTRACT_GET_SUPPORT_FRAGMENT, Bundle.class);
            return (android.support.v4.app.Fragment) method.invoke(null, screenParams.passProps);
        } catch (NoSuchMethodException noSuchMethod) {
            return null;
        }
    }

    @Override
    public void ensureUnmountOnDetachedFromWindow() {
        // nothing
    }

    @Override
    public void preventUnmountOnDetachedFromWindow() {
        // nothing
    }

    @Override
    public void preventMountAfterReattachedToWindow() {
        // nothing
    }
}
