package com.reactnativenavigation.views;

import android.view.View;
import android.widget.ScrollView;

public class ScrollViewAttacher {

    private final ScrollDirectionListener.OnScrollChanged onChanged;
    private ScrollView scrollView;
    private ScrollDirectionListener scrollDirectionListener;

    public ScrollViewAttacher(ScrollDirectionListener.OnScrollChanged onChanged) {
        this.onChanged = onChanged;
    }

    public void onViewAdded(View child) {
        if (child instanceof ScrollView) {
            detach();
            scrollView = (ScrollView) child;
            scrollDirectionListener = new ScrollDirectionListener(scrollView, onChanged);
            scrollView.getViewTreeObserver().addOnScrollChangedListener(scrollDirectionListener);
        }
    }

    public void detach() {
        if (scrollView != null) {
            scrollView.getViewTreeObserver().removeOnScrollChangedListener(scrollDirectionListener);
            scrollDirectionListener = null;
            scrollView = null;
        }
    }
}
