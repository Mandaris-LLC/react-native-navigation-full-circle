package com.reactnativenavigation.modal;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.reactnativenavigation.R;
import com.reactnativenavigation.activities.BaseReactActivity;
import com.reactnativenavigation.controllers.ModalController;
import com.reactnativenavigation.core.objects.Screen;
import com.reactnativenavigation.layouts.Layout;
import com.reactnativenavigation.layouts.OnScreenPoppedListener;
import com.reactnativenavigation.utils.ContextProvider;
import com.reactnativenavigation.utils.SdkSupports;
import com.reactnativenavigation.utils.StyleHelper;

public class RnnModal extends Dialog implements DialogInterface.OnDismissListener, OnScreenPoppedListener {

    private Layout contentView;

    public RnnModal(Context context, ModalController modalController, Screen screen) {
        super(context, R.style.Modal);
        modalController.add(this);
        init(context, screen);
    }

    @SuppressLint("InflateParams")
    private void init(final Context context, Screen screen) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        contentView = null;
        setContentView((View) contentView);

//        // Set navigation colors
//        if (SdkSupports.lollipop()) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            StyleHelper.setWindowStyle(window, context.getApplicationContext(), screen);
//        }
        setOnDismissListener(this);
    }

    public void push(Screen screen) {
        contentView.push(screen);
    }

    public Screen pop() {
        return contentView.pop();
    }

    @Override
    public void onScreenPopped(Screen popped) {
        if (contentView.getScreenCount() == 0) {
            dismiss();
        }
    }

    @Nullable
    public Screen getCurrentScreen() {
        return mScreenStack.isEmpty() ? null : mScreenStack.peek();
    }

    @Override
    public void onBackPressed() {
        if (mScreenStack.getStackSize() == 1) {
            super.onBackPressed();
        } else {
            pop();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mScreenStack.removeAllReactViews();
        ModalController.getInstance().remove();
        // After modal is dismissed, update Toolbar with screen from parent activity or previously displayed modal
        BaseReactActivity context = ContextProvider.getActivityContext();
        if (context != null) {
            Screen currentScreen = context.getCurrentScreen();
            StyleHelper.updateStyles(mToolBar, currentScreen);
        }
    }
}
