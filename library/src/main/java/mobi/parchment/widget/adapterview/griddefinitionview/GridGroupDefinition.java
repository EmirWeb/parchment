package mobi.parchment.widget.adapterview.griddefinitionview;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mobi.parchment.widget.adapterview.utilities.ViewGroupUtilities;

/**
 * Created by Emir Hasanbegovic on 2014-03-03.
 */
public class GridGroupDefinition {

    private final boolean mIsVerticalScroll;
    private final List<GridItemDefinition> mGridItemDefinitions = new ArrayList<GridItemDefinition>();

    public GridGroupDefinition(final boolean isVerticalScroll, final List<GridItemDefinition> gridItemDefinitions) {
        mIsVerticalScroll = isVerticalScroll;
        for (final GridItemDefinition gridItemDefinition : gridItemDefinitions) {
            addGridItemDefinition(gridItemDefinition);
        }
    }

    private List<Integer> mFirstGridGroupPositions = new ArrayList<Integer>();
    private List<Integer> mLastGridGroupPositions = new ArrayList<Integer>();

    private int mHeight;
    private int mWidth;


    public int getFirstGridGroupPosition(final int gridPosition) {

        for (int index = 0; index < mFirstGridGroupPositions.size(); index++) {
            final int gridGroupPosition = mFirstGridGroupPositions.get(index);
            if (gridGroupPosition < gridPosition) {
                return gridGroupPosition;
            }
        }

        return -1;
    }

    public int getLastGridGroupPosition(final int gridPosition) {
        for (int index = 0; index < mLastGridGroupPositions.size(); index++) {
            final int gridGroupPosition = mLastGridGroupPositions.get(index);
            if (gridGroupPosition < gridPosition) {
                return gridGroupPosition;
            }
        }

        return -1;
    }

    public int getFirstGridGroupPosition() {
        return mFirstGridGroupPositions.get(0);
    }

    public int getLastGridGroupPosition() {
        return mLastGridGroupPositions.get(0);
    }

    public void addGridItemDefinition(final GridItemDefinition gridItemDefinition) {
        mGridItemDefinitions.add(gridItemDefinition);
        final int gridPosition = mGridItemDefinitions.size() - 1;

        final int top = gridItemDefinition.getTop();
        final int left = gridItemDefinition.getLeft();
        final int width = gridItemDefinition.getWidth();
        final int height = gridItemDefinition.getHeight();
        final int bottom = top + height - 1;
        final int right = left + width - 1;
        final int maxHeight = bottom + 1;
        final int maxWidth = right + 1;

        if (mFirstGridGroupPositions.isEmpty() && mLastGridGroupPositions.isEmpty()) {
            mFirstGridGroupPositions.add(0);
            mLastGridGroupPositions.add(0);
            mWidth = maxWidth;
            mHeight = maxHeight;
            return;
        }

        if (mHeight < maxHeight) {
            mHeight = maxHeight;
        }

        if (mWidth < maxWidth) {
            mWidth = maxWidth;
        }

        {
            for (int index = 0; index < mFirstGridGroupPositions.size(); index++) {
                final GridItemDefinition currentFirstGridItemDefinition = mGridItemDefinitions.get(index);
                final int currentTop = currentFirstGridItemDefinition.getTop();
                final int currentLeft = currentFirstGridItemDefinition.getLeft();


                if (mIsVerticalScroll && top <= currentTop || !mIsVerticalScroll && left <= currentLeft) {
                    mFirstGridGroupPositions.add(index, gridPosition);
                    break;
                }
            }
        }

        {
            for (int index = 0; index < mLastGridGroupPositions.size(); index++) {
                final GridItemDefinition currentLastGridItemDefinition = mGridItemDefinitions.get(index);
                final int currentTop = currentLastGridItemDefinition.getTop();
                final int currentLeft = currentLastGridItemDefinition.getLeft();
                final int currentWidth = currentLastGridItemDefinition.getWidth();
                final int currentHeight = currentLastGridItemDefinition.getHeight();
                final int currentBottom = currentTop + currentHeight - 1;
                final int currentRight = currentLeft + currentWidth - 1;


                if (!mIsVerticalScroll && right >= currentRight || mIsVerticalScroll && bottom >= currentBottom) {
                    mLastGridGroupPositions.add(index, gridPosition);
                    break;
                }
            }
        }

    }

    public int getNumberOfItems() {
        return mGridItemDefinitions.size();
    }

    public List<GridItemDefinition> getGridItemDefinitions() {
        return mGridItemDefinitions;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getMeasuredHeight(final ViewGroup viewGroup, final int cellSpacing, final float ratio) {
        if (mIsVerticalScroll) {
            final int gridItemHeight = getGridItemHeight(viewGroup, cellSpacing, ratio);
            final int numberOfGridItemsPerColumn = getHeight();
            final int numberOfCellSpacings = numberOfGridItemsPerColumn - 1;
            return gridItemHeight * numberOfGridItemsPerColumn + numberOfCellSpacings * cellSpacing;
        }

        final int viewGroupHeight = ViewGroupUtilities.getViewGroupMeasuredHeight(viewGroup);
        return viewGroupHeight - 2 * cellSpacing;
    }

    public int getGridItemHeight(final ViewGroup viewGroup, final int cellSpacing, final float ratio) {
        if (mIsVerticalScroll) {
            final int gridItemWidth = getGridItemWidth(viewGroup, cellSpacing, ratio);
            return (int) (gridItemWidth * ratio);
        }


        final int viewGroupHeight = getMeasuredHeight(viewGroup, cellSpacing, ratio);
        final int numberOfGridItemsPerColumn = getHeight();
        final int numberOfCellSpacings = numberOfGridItemsPerColumn - 1;
        return (viewGroupHeight - numberOfCellSpacings * cellSpacing) / numberOfGridItemsPerColumn;

    }

    public int getGridItemWidth(final ViewGroup viewGroup, final int cellSpacing, final float ratio) {
        if (mIsVerticalScroll) {
            final int viewGroupWidth = getMeasuredWidth(viewGroup, cellSpacing, ratio);
            final int numberOfGridItemsPerRow = getWidth();
            final int numberOfCellSpacings = numberOfGridItemsPerRow - 1;
            return (viewGroupWidth - numberOfCellSpacings * cellSpacing) / numberOfGridItemsPerRow;
        }

        final int gridItemHeight = getGridItemHeight(viewGroup, cellSpacing, ratio);
        return (int) (gridItemHeight * ratio);

    }


    public int getMeasuredWidth(final ViewGroup viewGroup, final int cellSpacing, final float ratio) {
        if (mIsVerticalScroll) {
            final int viewGroupWidth = ViewGroupUtilities.getViewGroupMeasuredWidth(viewGroup);
            return viewGroupWidth - 2 * cellSpacing;
        }

        final int gridItemWidth = getGridItemWidth(viewGroup, cellSpacing, ratio);
        final int numberOfGridItemsPerRow = getWidth();
        final int numberOfCellSpacings = numberOfGridItemsPerRow - 1;
        return gridItemWidth * numberOfGridItemsPerRow + numberOfCellSpacings * cellSpacing;

    }

}
