package com.reactnativenavigation.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.reactnativenavigation.R;
import com.reactnativenavigation.core.objects._Screen;

/**
 * Created by guyc on 07/05/16.
 */
public class RnnTabLayout extends TabLayout {
    private Drawable mBackground;
    private ColorStateList mTabTextColors;
    private int mSelectedTabIndicatorColor;

    public RnnTabLayout(Context context) {
        this(context, null);
    }

    public RnnTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RnnTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context ctx) {
        mBackground = getBackground();
        mTabTextColors = getTabTextColors();

        // Get default accent color which is used as the selected tab indicator color
        TypedValue typedValue = new TypedValue();
        TypedArray a = ctx.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        mSelectedTabIndicatorColor = a.getColor(0, 0);
        a.recycle();
    }

    public void setStyle(_Screen screen) {
        if (screen.toolBarColor != null) {
            setBackgroundColor(screen.toolBarColor);
        } else {
            resetBackground();
        }

        if (screen.tabNormalTextColor != null && screen.tabSelectedTextColor != null) {
            setTabTextColors(screen.tabNormalTextColor, screen.tabSelectedTextColor);
        } else {
            resetTextColors();
        }

        if (screen.tabIndicatorColor != null) {
            setSelectedTabIndicatorColor(screen.tabIndicatorColor);
        } else {
            resetSelectedTabIndicatorColor();
        }
    }

    public void resetBackground() {
        setBackground(mBackground);
    }

    public void resetTextColors() {
        setTabTextColors(mTabTextColors);
    }

    public void resetSelectedTabIndicatorColor() {
        setSelectedTabIndicatorColor(mSelectedTabIndicatorColor);
    }
}
