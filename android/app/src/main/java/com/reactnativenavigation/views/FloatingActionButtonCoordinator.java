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
                collapsedFab.animate()
                        .alpha(0)
                        .setDuration(crossFadeAnimationDuration)
                        .rotation(90)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                collapsedFab.setVisibility(View.GONE);
                            }
                        })
                        .start();
                expendedFab.animate()
                        .alpha(1)
                        .setDuration(crossFadeAnimationDuration)
                        .rotation(0)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                expendedFab.setVisibility(View.VISIBLE);
                            }
                        })
                        .start();
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
                expendedFab.animate()
                        .alpha(0)
                        .setDuration(crossFadeAnimationDuration)
                        .rotation(-90)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                expendedFab.setVisibility(View.GONE);
                            }
                        })
                        .start();
                collapsedFab.animate()
                        .alpha(1)
                        .setDuration(crossFadeAnimationDuration)
                        .rotation(0)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                collapsedFab.setVisibility(View.VISIBLE);
                            }
                        })
                        .start();
            }
        });
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
}
