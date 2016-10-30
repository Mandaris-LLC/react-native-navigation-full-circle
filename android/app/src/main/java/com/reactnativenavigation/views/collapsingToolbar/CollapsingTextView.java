package com.reactnativenavigation.views.collapsingToolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.FloatRange;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.TintTypedArray;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reactnativenavigation.params.StyleParams;
import com.reactnativenavigation.utils.ViewUtils;

public class CollapsingTextView extends FrameLayout {
    private static final float TEXT_SCALE_FACTOR = 1.75f;
    private static final float INTERPOLATOR_EASING_FACTOR = 0.5f;

    private final int collapsedHeight;
    private TextView dummy;
    private String text;
    private Paint paint;
    private float initialY;
    private float currentY;
    private float collapseY;
    private float collapseFraction;
    private int[] positionOnScreen;
    private Interpolator scaleInterpolator;
    private float expendedTextSize;
    private float collapsedTextSize;

    public CollapsingTextView(Context context, int collapsedHeight) {
        super(context);
        this.collapsedHeight = collapsedHeight;
        setWillNotDraw(false);
        createDummyTextView(context);
        createTextPaint();
        scaleInterpolator = new DecelerateInterpolator(INTERPOLATOR_EASING_FACTOR);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
    }

    @SuppressLint("PrivateResource")
    private void createDummyTextView(Context context) {
        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), null,
                android.support.v7.appcompat.R.styleable.Toolbar, android.support.v7.appcompat.R.attr.toolbarStyle, 0);
        int titleTextAppearance =
                a.getResourceId(android.support.v7.appcompat.R.styleable.Toolbar_titleTextAppearance, 0);
        a.recycle();

        dummy = new TextView(context);
        TextViewCompat.setTextAppearance(dummy, titleTextAppearance);
        collapsedTextSize = dummy.getTextSize();
        expendedTextSize = collapsedTextSize * TEXT_SCALE_FACTOR;
        dummy.setTextSize(ViewUtils.convertPixelToSp(expendedTextSize));
        dummy.setVisibility(INVISIBLE);
        addView(dummy, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    private void createTextPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        paint.setColor(Color.GREEN);
        paint.setTextSize(expendedTextSize);

        ViewUtils.runOnPreDraw(dummy, new Runnable() {
            @Override
            public void run() {
                positionOnScreen = new int[2];
                getLocationInWindow(positionOnScreen);
                currentY = initialY = positionOnScreen[1] + getMeasuredHeight() - dummy.getMeasuredHeight();
                float bottomMargin = ViewUtils.convertDpToPixel(10);
                collapseY = positionOnScreen[1] + bottomMargin;
                invalidate();
            }
        });
    }

    public void setText(String text) {
        this.text = text;
        dummy.setText(text);
    }

    public void setTextColor(StyleParams params) {
        if (params.titleBarTitleColor.hasColor()) {
            paint.setColor(params.titleBarTitleColor.getColor());
        }
    }

    public void collapseBy(float translation) {
        collapseFraction = calculateFraction(translation);
        currentY = linearInterpolation(initialY, collapseY, collapseFraction);
        invalidate();
    }

    /*
    * A value of {@code 0.0} indicates that the layout is fully expanded.
    * A value of {@code 1.0} indicates that the layout is fully collapsed.
    */
    @FloatRange(from = 0.0, to = 1.0)
    private float calculateFraction(float translation) {
        return Math.abs(translation / (getMeasuredHeight() - collapsedHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextSize(linearInterpolation(expendedTextSize, collapsedTextSize, collapseFraction));
        canvas.drawText(text, 0, currentY, paint);
    }

    private float linearInterpolation(float from, float to, float fraction) {
        fraction = scaleInterpolator.getInterpolation(fraction);
        return from + (fraction * (to - from));
    }
}
