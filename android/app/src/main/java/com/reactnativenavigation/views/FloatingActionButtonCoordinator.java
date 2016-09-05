package com.reactnativenavigation.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.reactnativenavigation.params.FabParams;
import com.reactnativenavigation.utils.ViewUtils;

public class FloatingActionButtonCoordinator {

    private CoordinatorLayout parent;
    private FabParams params;
    FloatingActionButton collapsedFab;
    FloatingActionButton expendedFab;
    final int crossFadeAnimationDuration;

    public FloatingActionButtonCoordinator(CoordinatorLayout parent, FabParams params) {
        this.parent = parent;
        this.params = params;
        crossFadeAnimationDuration = parent.getResources().getInteger(android.R.integer.config_shortAnimTime);
        createCollapsedFab();
        createExpendedFab();
        setStyle();
    }

    private void createCollapsedFab() {
        collapsedFab = createFab(params.collapsedIcon);
        collapsedFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCollapsed();
                showExpended();
            }
        });
    }

    private void createExpendedFab() {
        expendedFab = createFab(params.expendedIcon);
        expendedFab.setVisibility(View.GONE);
        expendedFab.setRotation(-90);
        expendedFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideExpended();
                showCollapsed();
            }
        });
    }

    private void hideCollapsed() {
        animateFab(collapsedFab, 0, 90);
    }

    private void showExpended() {
        animateFab(expendedFab, 1, 0);
    }

    private void showCollapsed() {
        animateFab(collapsedFab, 1, 0);
    }

    private void hideExpended() {
        animateFab(expendedFab, 0, -90);
    }

    private void animateFab(final FloatingActionButton fab, final int alpha, int rotation) {
        fab.animate()
                .alpha(alpha)
                .setDuration(crossFadeAnimationDuration)
                .rotation(rotation)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        if (fab.getVisibility() == View.GONE) {
                            fab.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fab.setVisibility(alpha == 0 ? View.GONE : View.VISIBLE);
                    }
                })
                .start();
    }


    private FloatingActionButton createFab(Drawable icon) {
        FloatingActionButton fab = new FloatingActionButton(parent.getContext());
        fab.setImageDrawable(icon);
        parent.addView(fab, createFabLayoutParams());
        return fab;
    }

    private CoordinatorLayout.LayoutParams createFabLayoutParams() {
        final CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        final int margin = (int) ViewUtils.convertDpToPixel(16);
        lp.bottomMargin = margin;
        lp.rightMargin = margin;
        return lp;
    }

    private void setStyle() {

    }

    public void show() {

    }
}
