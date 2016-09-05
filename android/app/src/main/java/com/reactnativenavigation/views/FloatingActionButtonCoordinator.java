package com.reactnativenavigation.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.reactnativenavigation.params.FabActionParams;
import com.reactnativenavigation.params.FabParams;
import com.reactnativenavigation.utils.ViewUtils;

import java.util.ArrayList;

public class FloatingActionButtonCoordinator {

    private static final int INITIAL_EXPENDED_FAB_ROTATION = -90;
    private CoordinatorLayout parent;
    private FabParams params;
    private FloatingActionButton collapsedFab;
    private FloatingActionButton expendedFab;
    private final int crossFadeAnimationDuration;
    private final int actionSize;
    final int margin = (int) ViewUtils.convertDpToPixel(16);
    private final ArrayList<FloatingActionButton> actions;

    public FloatingActionButtonCoordinator(CoordinatorLayout parent, FabParams params) {
        this.parent = parent;
        this.params = params;
        actions = new ArrayList<>();
        crossFadeAnimationDuration = parent.getResources().getInteger(android.R.integer.config_shortAnimTime);
        actionSize = (int) ViewUtils.convertDpToPixel(40);
        createCollapsedFab();
        createExpendedFab();
        setStyle();
    }

    private void createCollapsedFab() {
        collapsedFab = createFab(params.collapsedIcon);
        parent.addView(collapsedFab, createFabLayoutParams());
        collapsedFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCollapsed();
                showExpended();
                showActions();
            }
        });
    }

    private void createExpendedFab() {
        expendedFab = createFab(params.expendedIcon);
        parent.addView(expendedFab, createFabLayoutParams());
        expendedFab.setVisibility(View.GONE);
        expendedFab.setRotation(INITIAL_EXPENDED_FAB_ROTATION);
        expendedFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideExpended();
                showCollapsed();
            }
        });
    }

    private FloatingActionButton createFab(Drawable icon) {
        FloatingActionButton fab = new FloatingActionButton(parent.getContext());
        fab.setId(ViewUtils.generateViewId());
        fab.setImageDrawable(icon);
        return fab;
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

    private CoordinatorLayout.LayoutParams createFabLayoutParams() {
        final CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        lp.bottomMargin = margin;
        lp.rightMargin = margin;
        lp.topMargin = margin;
        return lp;
    }

    private void setStyle() {

    }

    public void show() {

    }

    private void showActions() {
        if (actions.size() > 0) {
            return;
        }

        for (int i = 0; i < params.actions.size(); i++) {
            FloatingActionButton action = createAction(i);
            actions.add(action);
            parent.addView(action);
        }
    }

    private FloatingActionButton createAction(int index) {
        FabActionParams actionParams = params.actions.get(index);
        FloatingActionButton action = createFab(actionParams.icon);
        action.setLayoutParams(createActionLayoutParams(index));
     //   action.setAlpha(0);
        return action;
    }

    @NonNull
    private CoordinatorLayout.LayoutParams createActionLayoutParams(int actionIndex) {
        CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(actionSize, actionSize);
        lp.setAnchorId(expendedFab.getId());
        lp.anchorGravity = Gravity.CENTER_HORIZONTAL;
        lp.setBehavior(new ActionBehaviour(expendedFab, (actionIndex + 1) * (actionSize + margin / 2)));
        return lp;
    }

    public static class ActionBehaviour extends CoordinatorLayout.Behavior<FloatingActionButton> {
        private final int MAX_VALUE = 90;
        private int dependencyId;
        private float yStep;

        public ActionBehaviour(View anchor, float yStep) {
            this.yStep = yStep;
            this.dependencyId = anchor.getId();
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
            return dependency.getId() == dependencyId;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
            final float dependentValue = dependency.getRotation();
            float fraction = calculateTransitionFraction(dependentValue);
            child.setY(calculateY(parent, fraction));
            child.setAlpha(calculateAlpha(fraction));
            return true;
        }

        private float calculateAlpha(float fraction) {
            return 1 * fraction;
        }

        private float calculateY(CoordinatorLayout parent, float fraction) {
            return parent.findViewById(dependencyId).getY() - yStep * fraction;
        }

        @FloatRange(from=0.0, to=1.0)
        private float calculateTransitionFraction(float dependentValue) {
            return 1 - Math.abs(dependentValue / MAX_VALUE);
        }
    }
}
