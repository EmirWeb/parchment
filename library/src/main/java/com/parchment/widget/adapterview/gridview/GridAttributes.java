package com.parchment.widget.adapterview.gridview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.parchment.R;
import com.parchment.widget.adapterview.Attributes;


/**
 * Created by Emir Hasanbegovic on 2014-02-25.
 */
public class GridAttributes extends Attributes {

    private final int mNumberOfColumns;
    private boolean mIsLeft;
    private boolean mIsRight;
    private boolean mIsTop;
    private boolean mIsBottom;

    private static class DefaultValues {
        private static final int NUMBER_OF_COLUMNS = 1;
        private static final int GRAVITY = Gravity.TOP;
    }

    public GridAttributes(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (attributeSet != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.GridView, 0, 0);

            try {
                final int numberOfColumns = typedArray.getInteger(R.styleable.GridView_numberOfColumns, DefaultValues.NUMBER_OF_COLUMNS);
                mNumberOfColumns = Math.max(numberOfColumns, DefaultValues.NUMBER_OF_COLUMNS);

                final int gravity = typedArray.getInt(R.styleable.GridView_gravity, DefaultValues.GRAVITY);
                setGravityValues(gravity);
            } finally {
                typedArray.recycle();
            }
        } else {
            setGravityValues(DefaultValues.GRAVITY);
            mNumberOfColumns = DefaultValues.NUMBER_OF_COLUMNS;
        }
    }

    private void setGravityValues(int gravity) {
        mIsLeft = (gravity & Gravity.LEFT) != 0x0;
        mIsRight = (gravity & Gravity.RIGHT) != 0x0 && !mIsLeft;
        mIsTop = (gravity & Gravity.TOP) != 0x0;
        mIsBottom = (gravity & Gravity.BOTTOM) != 0x0 && !mIsTop;
    }

    public int getNumberOfColumns() {
        return mNumberOfColumns;
    }

    public boolean isLeft() {
        return mIsLeft;
    }

    public boolean isRight() {
        return mIsRight;
    }

    public boolean isTop() {
        return mIsTop;
    }

    public boolean isBottom() {
        return mIsBottom;
    }

}
