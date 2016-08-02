package com.reactnativenavigation.views;

import android.widget.ScrollView;

public class ScrollDirection {

    public enum Direction {
        Up, Down, None
    }

    private final ScrollView scrollView;
    private int lastScrollY = 0;

    public ScrollDirection(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public Direction getScrollDirection() {
        Direction direction = Direction.None;

        final int scrollY = scrollView.getScrollY();
        if (isScrollPositionChanged(scrollY) && !isTopOverscroll(scrollY) && !isBottomOverscroll(scrollY)) {
            direction = getScrollDirection(scrollY);
            lastScrollY = scrollY;
        }
        return direction;
    }

    public int getScrollDelta() {
        return scrollView.getScrollY() - lastScrollY;
    }


    private Direction getScrollDirection(int scrollY) {
        return scrollY > lastScrollY ? Direction.Up : Direction.Down;
    }

    private boolean isBottomOverscroll(int scrollY) {
        return scrollY >= (scrollView.getChildAt(0).getHeight() - scrollView.getHeight());
    }

    private boolean isTopOverscroll(int scrollY) {
        return scrollY <= 0;
    }

    private boolean isScrollPositionChanged(int scrollY) {
        return scrollY != lastScrollY;
    }

}
