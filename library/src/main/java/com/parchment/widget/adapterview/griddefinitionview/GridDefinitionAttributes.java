package com.parchment.widget.adapterview.griddefinitionview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.parchment.widget.adapterview.Attributes;
import com.parchment.widget.adapterview.Orientation;
import com.parchment.R;

/**
 * Created by Emir Hasanbegovic on 2014-02-25.
 */
public class GridDefinitionAttributes extends Attributes {

    private final float mRatio;

    private static class DefaultValues {
        private static final float RATIO = 1.0f;
    }

    public GridDefinitionAttributes(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (attributeSet != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.GridDefinitionView, 0, 0);

            try {

                final float ratio = typedArray.getFloat(R.styleable.GridDefinitionView_ratio, DefaultValues.RATIO);
                mRatio = ratio;
            } finally {
                typedArray.recycle();
            }
        } else {
            mRatio = DefaultValues.RATIO;
        }
    }

    public float getRatio() {
        return mRatio;
    }
}
