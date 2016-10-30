package com.reactnativenavigation.views.collapsingToolbar;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.reactnativenavigation.utils.ViewUtils;

class CollapseCalculator {
    private float collapse;
    private MotionEvent previousTouchEvent;
    private float touchDownY = -1;
    private float previousCollapseY = -1;
    private boolean isExpended;
    private boolean isCollapsed = true;
    private boolean canCollapse = true;
    private boolean canExpend = false;
    private CollapsingView view;
    protected ScrollView scrollView;
    private static float finalCollapsedTranslation;

    CollapseCalculator(final CollapsingView collapsingView) {
        this.view = collapsingView;
        ViewUtils.runOnPreDraw(view.asView(), new Runnable() {
            @Override
            public void run() {
                finalCollapsedTranslation = view.getFinalCollapseValue();
            }
        });
    }

    void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    @NonNull
    CollapseAmount calculate(MotionEvent event) {
        updateInitialTouchY(event);
        if (!isMoveEvent(event)) {
            return CollapseAmount.NONE;
        }

        if (shouldTranslateTopBarAndScrollView(event)) {
            return calculateCollapse(event);
        } else {
            previousCollapseY = -1;
            previousTouchEvent = MotionEvent.obtain(event);
            return CollapseAmount.NONE;
        }
    }

    private boolean shouldTranslateTopBarAndScrollView(MotionEvent event) {
        checkCollapseLimits();
        ScrollDirection.Direction direction = getScrollDirection(event.getRawY());
        return isMoveEvent(event) &&
               (isNotCollapsedOrExpended() ||
                (canCollapse && isExpendedAndScrollingUp(direction)) ||
                (canExpend && isCollapsedAndScrollingDown(direction)));
    }

    private ScrollDirection.Direction getScrollDirection(float y) {
        if (y == (previousCollapseY == -1 ? touchDownY : previousCollapseY)) {
            return ScrollDirection.Direction.None;
        }
        if (previousTouchEvent == null) {
            return ScrollDirection.Direction.None;
        }
        return y < previousTouchEvent.getRawY() ?
                ScrollDirection.Direction.Up :
                ScrollDirection.Direction.Down;
    }

    private void checkCollapseLimits() {
        float currentTopBarTranslation = view.getCurrentCollapseValue();
        float finalExpendedTranslation = 0;
        isExpended = isExpended(currentTopBarTranslation, finalExpendedTranslation);
        isCollapsed = isCollapsed(currentTopBarTranslation, finalCollapsedTranslation);
        canCollapse = calculateCanCollapse(currentTopBarTranslation, finalExpendedTranslation, finalCollapsedTranslation);
        canExpend = calculateCanExpend(currentTopBarTranslation, finalExpendedTranslation, finalCollapsedTranslation);
    }

    private boolean calculateCanCollapse(float currentTopBarTranslation, float finalExpendedTranslation, float finalCollapsedTranslation) {
        return currentTopBarTranslation > finalCollapsedTranslation &&
               currentTopBarTranslation <= finalExpendedTranslation;
    }

    private boolean calculateCanExpend(float currentTopBarTranslation, float finalExpendedTranslation, float finalCollapsedTranslation) {
        return currentTopBarTranslation >= finalCollapsedTranslation &&
               currentTopBarTranslation < finalExpendedTranslation &&
               scrollView.getScrollY() == 0;
    }

    private boolean isCollapsedAndScrollingDown(ScrollDirection.Direction direction) {
        return isCollapsed && direction == ScrollDirection.Direction.Down;
    }

    private boolean isExpendedAndScrollingUp(ScrollDirection.Direction direction) {
        return isExpended && direction == ScrollDirection.Direction.Up;
    }

    private  boolean isNotCollapsedOrExpended() {
        return canExpend && canCollapse;
    }

    private boolean isCollapsed(float currentTopBarTranslation, float finalCollapsedTranslation) {
        return currentTopBarTranslation == finalCollapsedTranslation;
    }

    private boolean isExpended(float currentTopBarTranslation, float finalExpendedTranslation) {
        return currentTopBarTranslation == finalExpendedTranslation;
    }

    private CollapseAmount calculateCollapse(MotionEvent event) {
        float y = event.getRawY();
        if (previousCollapseY == -1) {
            previousCollapseY = y;
        }
        collapse = calculateCollapse(y);
        previousCollapseY = y;
        previousTouchEvent = MotionEvent.obtain(event);
        return new CollapseAmount(collapse);
    }

    private float calculateCollapse(float y) {
        float translation = y - previousCollapseY + view.getCurrentCollapseValue();
        if (translation < finalCollapsedTranslation) {
            translation = finalCollapsedTranslation;
        }
        final float expendedTranslation = 0;
        if (translation > expendedTranslation) {
            translation = expendedTranslation;
        }
        return translation;
    }


    private void updateInitialTouchY(MotionEvent event) {
        if (isTouchDown(previousTouchEvent) && isMoveEvent(event)) {
            saveInitialTouchY(previousTouchEvent);
        } else if (isTouchUp(event) && isMoveEvent(previousTouchEvent)) {
            clearInitialTouchY();
        }
    }

    private boolean isMoveEvent(@Nullable MotionEvent event) {
        return event != null && event.getActionMasked() == MotionEvent.ACTION_MOVE;
    }

    private boolean isTouchDown(@Nullable MotionEvent event) {
        return event != null && event.getActionMasked() == MotionEvent.ACTION_DOWN;
    }

    private boolean isTouchUp(@Nullable MotionEvent event) {
        return event != null && event.getActionMasked() == MotionEvent.ACTION_UP;
    }

    private void saveInitialTouchY(MotionEvent event) {
        touchDownY = event.getRawY();
        previousCollapseY = touchDownY;
    }

    private void clearInitialTouchY() {
        touchDownY = -1;
        previousCollapseY = -1;
        collapse = 0;
    }
}
