package mobi.parchment.widget.adapterview.gridview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import mobi.parchment.R;
import mobi.parchment.widget.adapterview.Attributes;


/**
 * Created by Emir Hasanbegovic on 2014-02-25.
 */
public class GridAttributes extends Attributes {

    private final int mNumberOfViewsPerCell;
    private boolean mIsLeft;
    private boolean mIsRight;
    private boolean mIsTop;
    private boolean mIsBottom;
    private boolean mIsPerfectGrid;

    private static class DefaultValues {
        private static final int NUMBER_OF_VIEWS_PER_CELL = 1;
        private static final boolean IS_PERFECT_GRID = true;
        private static final int GRAVITY = Gravity.TOP;
    }

    public GridAttributes(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (attributeSet != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.GridView, 0, 0);

            try {
                final int numberOfColumns = typedArray.getInteger(R.styleable.GridView_numberOfViewsPerCell, DefaultValues.NUMBER_OF_VIEWS_PER_CELL);
                mNumberOfViewsPerCell = Math.max(numberOfColumns, DefaultValues.NUMBER_OF_VIEWS_PER_CELL);

                final int gravity = typedArray.getInt(R.styleable.GridView_gravity, DefaultValues.GRAVITY);
                setGravityValues(gravity);

                mIsPerfectGrid = typedArray.getBoolean(R.styleable.GridDefinitionView_isPerfectGrid, DefaultValues.IS_PERFECT_GRID);
            } finally {
                typedArray.recycle();
            }
        } else {
            setGravityValues(DefaultValues.GRAVITY);
            mNumberOfViewsPerCell = DefaultValues.NUMBER_OF_VIEWS_PER_CELL;
            mIsPerfectGrid = DefaultValues.IS_PERFECT_GRID;
        }
    }

    private void setGravityValues(int gravity) {
        mIsLeft = (gravity & Gravity.LEFT) != 0x0;
        mIsRight = (gravity & Gravity.RIGHT) != 0x0 && !mIsLeft;
        mIsTop = (gravity & Gravity.TOP) != 0x0;
        mIsBottom = (gravity & Gravity.BOTTOM) != 0x0 && !mIsTop;
    }

    public int getNumberOfViewsPerCell() {
        return mNumberOfViewsPerCell;
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

    public boolean isPerfectGrid() {
        return mIsPerfectGrid;
    }

}
