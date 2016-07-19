package com.reactnativenavigation.modal;

public class RnnModal {
//
//    private Layout contentView;
//
//    public RnnModal(Context context, ModalController modalController, _Screen screen) {
//        super(context, R.style.Modal);
//        modalController.add(this);
//        init(context, screen);
//    }
//
//    @SuppressLint("InflateParams")
//    private void init(final Context context, _Screen screen) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        contentView = null;
//        setContentView((View) contentView);
//
////        // Set navigation colors
////        if (SdkSupports.lollipop()) {
////            Window window = getWindow();
////            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
////            StyleHelper.setWindowStyle(window, context.getApplicationContext(), screen);
////        }
//        setOnDismissListener(this);
//    }
//
//    public void push(_Screen screen) {
//        contentView.push(screen);
//    }
//
//    public _Screen pop() {
//        return contentView.pop();
//    }
//
//    @Override
//    public void onScreenPopped(_Screen popped) {
//        if (contentView.getScreenCount() == 0) {
//            dismiss();
//        }
//    }
//
//    @Nullable
//    public _Screen getCurrentScreen() {
//        return mScreenStack.isEmpty() ? null : mScreenStack.peek();
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mScreenStack.getStackSize() == 1) {
//            super.onBackPressed();
//        } else {
//            pop();
//        }
//    }
//
//    @Override
//    public void onDismiss(DialogInterface dialog) {
//        mScreenStack.removeAllReactViews();
//        ModalController.getInstance().remove();
//        // After modal is dismissed, update Toolbar with screen from parent activity or previously displayed modal
//        BaseReactActivity context = ContextProvider.getActivityContext();
//        if (context != null) {
//            _Screen currentScreen = context.getCurrentScreen();
//            StyleHelper.updateStyles(mToolBar, currentScreen);
//        }
//    }
}
