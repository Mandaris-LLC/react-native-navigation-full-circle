package com.reactnativenavigation.views.collapsingToolbar;

public class CollapseAmount {
    public final static CollapseAmount None = new CollapseAmount();
    private Float amount;

    public CollapseAmount(float amount) {
        this.amount = amount;
    }

    private CollapseAmount() {

    }

    public boolean canCollapse() {
        return amount != null;
    }

    public float get() {
        return amount;
    }
}
