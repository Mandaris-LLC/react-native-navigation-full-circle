package com.reactnativenavigation.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.reactnativenavigation.utils.ViewUtils;

public class ScrollViewAttacher implements View.OnAttachStateChangeListener {

    private final ContentView view;
    private final ScrollDirectionListener.OnScrollChanged onChanged;
    private ScrollView scrollView;
    private ScrollDirectionListener scrollDirectionListener;

    public ScrollViewAttacher(ContentView view, ScrollDirectionListener.OnScrollChanged onChanged) {
        this.view = view;
        this.onChanged = onChanged;
    }

    public void attach() {
        ViewUtils.runOnPreDraw(view, new Runnable() {
            @Override
            public void run() {
                findScrollAndStartListening();
            }
        });
    }

    public void detach() {
        if (scrollView != null) {
            scrollView.removeOnAttachStateChangeListener(this);
            scrollView.getViewTreeObserver().removeOnScrollChangedListener(scrollDirectionListener);
            scrollDirectionListener = null;
            scrollView = null;
        }
    }

    private void findScrollAndStartListening() {
        scrollView = findScrollView(view);
        if (scrollView != null) {
            scrollDirectionListener = new ScrollDirectionListener(scrollView, onChanged);
            scrollView.getViewTreeObserver().addOnScrollChangedListener(scrollDirectionListener);
            scrollView.addOnAttachStateChangeListener(this);
        }
    }

    private ScrollView findScrollView(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);

            if (child instanceof ScrollView) {
                return (ScrollView) child;
            } else if (child instanceof ViewGroup) {
                return findScrollView((ViewGroup) child);
            }
        }

        return null;
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        // nothing
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        detach();
        attach();
    }
}
