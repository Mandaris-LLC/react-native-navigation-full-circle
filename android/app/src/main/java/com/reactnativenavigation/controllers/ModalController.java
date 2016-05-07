package com.reactnativenavigation.controllers;

import android.support.annotation.Nullable;

import com.reactnativenavigation.modal.RnnModal;
import com.reactnativenavigation.utils.RefUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by guyc on 06/05/16.
 */
public class ModalController {
    private static ModalController sInstance;

    private final Map<String, WeakReference<RnnModal>> mModals;

    private ModalController() {
        mModals = new HashMap<>();
    }

    public static synchronized ModalController getInstance() {
        if (sInstance == null) {
            sInstance = new ModalController();
        }

        return sInstance;
    }

    public void add(RnnModal modal, String navigatorId) {
        mModals.put(navigatorId, new WeakReference<>(modal));
    }

    public boolean isModalDisplayed() {
        return mModals.size() != 0;
    }

    public boolean isModalDisplayed(String navigatorId) {
        return mModals.size() != 0 && mModals.containsKey(navigatorId);
    }

    @Nullable
    public RnnModal get(String navigatorId) {
        if (mModals.containsKey(navigatorId)) {
            return RefUtils.get(mModals.get(navigatorId));
        }

        return null;
    }

    public void remove(String navigatorId) {
        if (mModals.containsKey(navigatorId)) {
            mModals.remove(navigatorId);
        }
    }

    public void dismissAllModals() {
        Iterator<String> iterator = mModals.keySet().iterator();
        while (iterator.hasNext()) {
            WeakReference<RnnModal> ref = mModals.get(iterator.next());
            RnnModal modal = RefUtils.get(ref);
            if (modal != null) {
                modal.dismiss();
            }
        }
    }
}
