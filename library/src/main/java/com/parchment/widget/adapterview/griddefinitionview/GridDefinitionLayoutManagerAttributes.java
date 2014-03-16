package com.parchment.widget.adapterview.griddefinitionview;

import com.parchment.widget.adapterview.LayoutManagerAttributes;
import com.parchment.widget.adapterview.SnapPosition;

/**
 * Created by Emir Hasanbegovic on 2014-02-25.
 */
public class GridDefinitionLayoutManagerAttributes extends LayoutManagerAttributes {
    private final float mRatio;

    public GridDefinitionLayoutManagerAttributes(final boolean isCircularScroll, final boolean snapToPosition, final boolean isViewPager, final int viewPagerInterval, final SnapPosition snapPosition, final int cellSpacing, final boolean selectOnSnap, final boolean selectWhileScrolling, final boolean isVertical, final float ratio) {
        super(isCircularScroll, snapToPosition, isViewPager, viewPagerInterval, snapPosition, cellSpacing, selectOnSnap, selectWhileScrolling, isVertical);
        mRatio = ratio;
    }

    public float getRatio() {
        return mRatio;
    }
}
