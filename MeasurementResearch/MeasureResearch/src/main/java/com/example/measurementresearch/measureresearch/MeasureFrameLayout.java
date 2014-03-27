package com.example.measurementresearch.measureresearch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by emir on 23/03/14.
 */
public class MeasureFrameLayout extends FrameLayout {
    private final static String TAG = "MeasureFrameLayout";
    public MeasureFrameLayout(Context context) {
        super(context);
    }

    public MeasureFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int width = MeasureSpec.getSize(widthMeasureSpec);

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);

        Log.d(TAG, "MeasureSpec.AT_MOST: " + MeasureSpec.AT_MOST);
        Log.d(TAG, "MeasureSpec.EXACTLY: " + MeasureSpec.EXACTLY);
        Log.d(TAG, "MeasureSpec.UNSPECIFIED: " + MeasureSpec.UNSPECIFIED);

        Log.d(TAG, "widthMode: " + widthMode + " width: " + width + " heightMode: " + heightMode + " height: " + height);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
