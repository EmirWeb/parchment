package mobi.parchment.widget.adapterview.griddefinitionview;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mobi.parchment.widget.adapterview.utilities.ViewGroupUtilities;

/**
 * Created by Emir Hasanbegovic on 2014-03-03.
 */
public class DefinitionGroup {

    private final GridGroupDefinition mGridGroupDefinition;
    private final ViewGroup mViewGroup;
    private final boolean mIsVerticalScroll;
    private final float mStretchRatio;
    private final int mCellSpacing;
    private int mStartOffset;

    private final List<View> mViews = new ArrayList<View>();

    public DefinitionGroup(final GridGroupDefinition gridGroupDefinition, final ViewGroup viewGroup, final boolean isVerticalScroll, final float stretchRatio, final int cellSpacing) {
        mGridGroupDefinition = gridGroupDefinition;
        mViewGroup = viewGroup;
        mIsVerticalScroll = isVerticalScroll;
        mStretchRatio = stretchRatio;
        mCellSpacing = cellSpacing;
    }

    public void setStartOffset(final int startOffset) {
        mStartOffset = startOffset;
    }

    public int getTop() {
        if (mIsVerticalScroll) {
            return mStartOffset;
        }

        return mCellSpacing;
    }

    public GridGroupDefinition getGridGroupDefinition() {
        return mGridGroupDefinition;
    }

    public int getBottom() {
        if (mIsVerticalScroll) {
            final int height = mGridGroupDefinition.getMeasuredHeight(mViewGroup, mCellSpacing, mStretchRatio);
            return mStartOffset + height;
        }

        final int viewGroupHeight = ViewGroupUtilities.getViewGroupMeasuredHeight(mViewGroup);
        return viewGroupHeight - mCellSpacing;
    }

    public int getLeft() {
        final View firstView = getFirstView();
        final int left = firstView.getLeft();
        return left;
    }

    public int getRight() {
        final View lastView = getLastView();
        final int right = lastView.getRight();
        return right;
    }

    public View getLastView() {
        final int numberOfItems = getNumberOfItems();
        final int lastGridGroupPosition = mGridGroupDefinition.getLastGridGroupPosition(numberOfItems);
        final View lastView = mViews.get(lastGridGroupPosition);
        return lastView;
    }

    public View getFirstView() {
        final int numberOfItems = getNumberOfItems();
        final int firstGridGroupPosition = mGridGroupDefinition.getFirstGridGroupPosition(numberOfItems);
        final View firstView = mViews.get(firstGridGroupPosition);
        return firstView;
    }

    public void clear() {
        mViews.clear();
    }

    public int getNumberOfItems() {
        return mViews.size();
    }

    public void addView(final View view) {
        mViews.add(view);
    }

    public int getBreadth() {
        if (!mIsVerticalScroll) {
            return mGridGroupDefinition.getMeasuredHeight(mViewGroup, mCellSpacing, mStretchRatio);
        }

        return mGridGroupDefinition.getMeasuredWidth(mViewGroup, mCellSpacing, mStretchRatio);
    }

    public boolean isEmpty() {
        return mViews.isEmpty();
    }

    public List<View> getViews() {
        return mViews;
    }
}
