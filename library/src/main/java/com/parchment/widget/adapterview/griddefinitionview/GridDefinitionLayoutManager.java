package com.parchment.widget.adapterview.griddefinitionview;

import android.view.View;
import android.view.ViewGroup;

import com.parchment.widget.adapterview.AdapterViewManager;
import com.parchment.widget.adapterview.LayoutManager;
import com.parchment.widget.adapterview.LayoutManagerAttributes;
import com.parchment.widget.adapterview.OnSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emir Hasanbegovic on 2014-03-03.
 */
public class GridDefinitionLayoutManager extends LayoutManager<DefinitionGroup> {

    private List<GridGroupDefinition> mGridGroupDefinitions = new ArrayList<GridGroupDefinition>();
    private int mNumberOfGridItemsPerRepetition;
    private GridDefinitionLayoutManagerAttributes mGridDefinitionLayoutManagerAttributes;

    public GridDefinitionLayoutManager(final ViewGroup viewGroup, final OnSelectedListener onSelectedListener, final AdapterViewManager adapterViewManager, final LayoutManagerAttributes layoutManagerAttributes) {
        super(viewGroup, onSelectedListener, adapterViewManager, layoutManagerAttributes);
        mGridDefinitionLayoutManagerAttributes = (GridDefinitionLayoutManagerAttributes) layoutManagerAttributes;
    }

    public void addGridGroupDefinition(final GridGroupDefinition gridGroupDefinition) {
        if (gridGroupDefinition == null) {
            return;
        }
        mGridGroupDefinitions.add(gridGroupDefinition);
        mNumberOfGridItemsPerRepetition += gridGroupDefinition.getNumberOfItems();
    }

    public void setGridGroupDefinitions(final List<GridGroupDefinition> gridGroupDefinitions) {

        if (gridGroupDefinitions == null) {
            mGridGroupDefinitions.clear();
            mNumberOfGridItemsPerRepetition = 0;
            return;
        }

        mGridGroupDefinitions = new ArrayList<GridGroupDefinition>(gridGroupDefinitions);
        int numberOfGridItemsPerRepetition = 0;
        for (final GridGroupDefinition gridGroupDefinition : mGridGroupDefinitions) {
            numberOfGridItemsPerRepetition += gridGroupDefinition.getNumberOfItems();
        }
        mNumberOfGridItemsPerRepetition = numberOfGridItemsPerRepetition;
    }

    @Override
    public View getLastView(final DefinitionGroup definitionGroup) {
        final View lastView = definitionGroup.getLastView();
        return lastView;
    }

    @Override
    public View getView(final DefinitionGroup definitionGroup) {
        final View representativeView = definitionGroup.getFirstView();
        return representativeView;
    }

    @Override
    public View getFirstView(final DefinitionGroup definitionGroup) {
        final View firstView = definitionGroup.getFirstView();
        return firstView;
    }

    @Override
    public int getCellStart(final DefinitionGroup definitionGroup) {
        if (isVerticalScroll()) {
            return definitionGroup.getTop();
        }
        return definitionGroup.getLeft();
    }

    @Override
    public int getCellEnd(final DefinitionGroup definitionGroup) {
        if (isVerticalScroll()) {
            return definitionGroup.getBottom();
        }
        return definitionGroup.getRight();
    }

    @Override
    public int getCellSize(final DefinitionGroup definitionGroup) {
        final GridGroupDefinition gridGroupDefinition = definitionGroup.getGridGroupDefinition();
        final ViewGroup viewGroup = getViewGroup();
        final int cellSpacing = mGridDefinitionLayoutManagerAttributes.getCellSpacing();
        final float ratio = mGridDefinitionLayoutManagerAttributes.getRatio();
        if (isVerticalScroll()) {
            return gridGroupDefinition.getMeasuredHeight(viewGroup, cellSpacing, ratio);
        }
        return gridGroupDefinition.getMeasuredWidth(viewGroup, cellSpacing, ratio);
    }

    @Override
    public List<View> getViews(final DefinitionGroup definitionGroup) {
        final List<View> views = definitionGroup.getViews();
        return views;
    }

    @Override
    public DefinitionGroup getCell(final int adapterPosition) {
        final GridGroupDefinition gridGroupDefinition = getGridGroupDefinition(adapterPosition);
        final ViewGroup viewGroup = getViewGroup();
        final int cellSpacing = getCellSpacing();
        final float ratio = mGridDefinitionLayoutManagerAttributes.getRatio();
        final boolean isVerticalScroll = isVerticalScroll();
        final DefinitionGroup definitionGroup = new DefinitionGroup(gridGroupDefinition, viewGroup, isVerticalScroll, ratio, cellSpacing);

        final AdapterViewManager adapterViewManager = getAdapterViewManager();
        final int adapterCount = adapterViewManager.getAdapterCount();
        final int numberOfItems = gridGroupDefinition.getNumberOfItems();
        final int positionLimit = Math.min(adapterCount, adapterPosition + numberOfItems);
        for (int position = adapterPosition; position < positionLimit; position++) {
            final int gridItemDefinitionPosition = position - adapterPosition;
            final GridItemDefinition gridItemDefinition = gridGroupDefinition.getGridItemDefinitions().get(gridItemDefinitionPosition);
            final int maxMeasureWidth = getMaxMeasuredWidth(gridGroupDefinition, gridItemDefinition, cellSpacing);
            final int maxMeasureHeight = getMaxMeasuredHeight(gridGroupDefinition, gridItemDefinition, cellSpacing);
            final View view = adapterViewManager.getView(mViewGroup, position, maxMeasureWidth, maxMeasureHeight);
            definitionGroup.addView(view);
        }

        return definitionGroup;
    }

    private int getGridItemDefinitionBreadthStart(final GridItemDefinition gridItemDefinition) {
        if (isVerticalScroll()) {
            return gridItemDefinition.getLeft();
        }
        return gridItemDefinition.getTop();
    }

    private int getGridItemDefinitionSizeStart(final GridItemDefinition gridItemDefinition) {
        if (isVerticalScroll()) {
            return gridItemDefinition.getTop();
        }
        return gridItemDefinition.getLeft();
    }

    private int getGridItemDefinitionSize(final GridItemDefinition gridItemDefinition) {
        if (isVerticalScroll()) {
            return gridItemDefinition.getHeight();
        }
        return gridItemDefinition.getWidth();
    }

    private int getGridItemDefinitionBreadth(final GridItemDefinition gridItemDefinition) {
        if (isVerticalScroll()) {
            return gridItemDefinition.getWidth();
        }
        return gridItemDefinition.getHeight();
    }

    private int getGridItemSize(final GridGroupDefinition gridGroupDefinition) {
        final ViewGroup viewGroup = getViewGroup();
        final int cellSpacing = mGridDefinitionLayoutManagerAttributes.getCellSpacing();
        final float ratio = mGridDefinitionLayoutManagerAttributes.getRatio();
        if (isVerticalScroll()) {
            return gridGroupDefinition.getGridItemHeight(viewGroup, cellSpacing, ratio);
        }
        return gridGroupDefinition.getGridItemWidth(viewGroup, cellSpacing, ratio);
    }

    private int getGridItemBreadth(final GridGroupDefinition gridGroupDefinition) {
        final ViewGroup viewGroup = getViewGroup();
        final int cellSpacing = mGridDefinitionLayoutManagerAttributes.getCellSpacing();
        final float ratio = mGridDefinitionLayoutManagerAttributes.getRatio();
        if (isVerticalScroll()) {
            return gridGroupDefinition.getGridItemWidth(viewGroup, cellSpacing, ratio);
        }
        return gridGroupDefinition.getGridItemHeight(viewGroup, cellSpacing, ratio);
    }

    private int getGridItemSizeStartOffset(final GridGroupDefinition gridGroupDefinition, final GridItemDefinition gridItemDefinition, final int cellSpacing) {
        final int gridItemDefinitionStart = getGridItemDefinitionSizeStart(gridItemDefinition);
        final int cellSpacingCount = gridItemDefinitionStart;
        final int gridItemPixelSize = getGridItemSize(gridGroupDefinition);
        final int gridItemStartOffset = cellSpacingCount * cellSpacing + gridItemDefinitionStart * gridItemPixelSize;
        return gridItemStartOffset;
    }

    private int getGridItemBreadthStartOffset(final GridGroupDefinition gridGroupDefinition, final GridItemDefinition gridItemDefinition, final int cellSpacing) {
        final int gridItemDefinitionStart = getGridItemDefinitionBreadthStart(gridItemDefinition);
        final int cellSpacingCount = gridItemDefinitionStart;
        final int gridItemPixelSize = getGridItemBreadth(gridGroupDefinition);
        final int gridItemStartOffset = cellSpacingCount * cellSpacing + gridItemDefinitionStart * gridItemPixelSize;
        return gridItemStartOffset;
    }

    private int getViewSize(final GridGroupDefinition gridGroupDefinition, final GridItemDefinition gridItemDefinition, final int cellSpacing) {
        final int gridItemDefinitionSize = getGridItemDefinitionSize(gridItemDefinition);
        final int cellSpacingCount = gridItemDefinitionSize - 1;
        final int gridItemSize = getGridItemSize(gridGroupDefinition);
        final int viewSize = gridItemDefinitionSize * gridItemSize + cellSpacingCount * cellSpacing;

        return viewSize;
    }

    private int getViewBreadth(final GridGroupDefinition gridGroupDefinition, final GridItemDefinition gridItemDefinition, final int cellSpacing) {
        final int gridItemDefinitionSize = getGridItemDefinitionBreadth(gridItemDefinition);
        final int cellSpacingCount = gridItemDefinitionSize - 1;
        final int gridItemSize = getGridItemBreadth(gridGroupDefinition);
        final int viewSize = gridItemDefinitionSize * gridItemSize + cellSpacingCount * cellSpacing;

        return viewSize;
    }

    @Override
    public void layoutCell(final DefinitionGroup definitionGroup, final int cellStart, final int cellEnd, final int firstAdapterPositionInCell, final int breadth, final int cellSpacing) {
        definitionGroup.setStartOffset(cellStart);
        final GridGroupDefinition gridGroupDefinition = definitionGroup.getGridGroupDefinition();
        final List<GridItemDefinition> gridItemDefinitions = gridGroupDefinition.getGridItemDefinitions();

        final List<View> views = definitionGroup.getViews();
        final int limit = views.size();
        int adapterPosition = firstAdapterPositionInCell;
        for (int index = 0; index < limit; index++) {
            final View view = views.get(index);
            final GridItemDefinition gridItemDefinition = gridItemDefinitions.get(index);

            final int viewSize = getViewSize(gridGroupDefinition, gridItemDefinition, cellSpacing);
            final int gridItemSizeStartOffset = getGridItemSizeStartOffset(gridGroupDefinition, gridItemDefinition, cellSpacing);
            final int viewSizeStart = cellStart + gridItemSizeStartOffset;
            final int viewSizeEnd = viewSizeStart + viewSize;

            final int viewBreadth = getViewBreadth(gridGroupDefinition, gridItemDefinition, cellSpacing);
            final int gridItemBreadthStartOffset = getGridItemBreadthStartOffset(gridGroupDefinition, gridItemDefinition, cellSpacing);
            final int viewBreadthStart = gridItemBreadthStartOffset + cellSpacing;
            final int viewBreadthEnd = viewBreadthStart + viewBreadth;

            final boolean isSelected = isViewSelected(adapterPosition++);
            view.setSelected(isSelected);

            if (isVerticalScroll()) {
                view.layout(viewBreadthStart, viewSizeStart, viewBreadthEnd, viewSizeEnd);
            } else {
                view.layout(viewSizeStart, viewBreadthStart, viewSizeEnd, viewBreadthEnd);
            }
        }
    }

    @Override
    protected int getMaxMeasureHeight(final int adapterPosition) {
        final int cellPosition = getCellPosition(adapterPosition);
        final int cellPositionOffset = cellPosition % mGridGroupDefinitions.size();
        final GridGroupDefinition gridGroupDefinition = mGridGroupDefinitions.get(cellPositionOffset);
        final int firstAdapterPosition = getFirstAdapterPositionInCell(cellPosition);
        final int gridItemPosition = adapterPosition - firstAdapterPosition;
        final GridItemDefinition gridItemDefinition = gridGroupDefinition.getGridItemDefinitions().get(gridItemPosition);
        final int cellSpacing = mGridDefinitionLayoutManagerAttributes.getCellSpacing();
        return getMaxMeasuredHeight(gridGroupDefinition, gridItemDefinition, cellSpacing);
    }

    @Override
    protected int getMaxMeasureWidth(final int adapterPosition) {
        final int cellPosition = getCellPosition(adapterPosition);
        final int cellPositionOffset = cellPosition % mGridGroupDefinitions.size();
        final GridGroupDefinition gridGroupDefinition = mGridGroupDefinitions.get(cellPositionOffset);
        final int firstAdapterPosition = getFirstAdapterPositionInCell(cellPosition);
        final int gridItemPosition = adapterPosition - firstAdapterPosition;
        final GridItemDefinition gridItemDefinition = gridGroupDefinition.getGridItemDefinitions().get(gridItemPosition);
        final int cellSpacing = mGridDefinitionLayoutManagerAttributes.getCellSpacing();
        return getMaxMeasuredWidth(gridGroupDefinition, gridItemDefinition, cellSpacing);
    }

    private int getMaxMeasuredHeight(final GridGroupDefinition gridGroupDefinition, final GridItemDefinition gridItemDefinition, final int cellSpacing) {
        if (isVerticalScroll()) {
            return getViewSize(gridGroupDefinition, gridItemDefinition, cellSpacing);
        }
        return getViewBreadth(gridGroupDefinition, gridItemDefinition, cellSpacing);
    }

    private int getMaxMeasuredWidth(final GridGroupDefinition gridGroupDefinition, final GridItemDefinition gridItemDefinition, final int cellSpacing) {
        if (isVerticalScroll()) {
            return getViewBreadth(gridGroupDefinition, gridItemDefinition, cellSpacing);
        }
        return getViewSize(gridGroupDefinition, gridItemDefinition, cellSpacing);
    }

    @Override
    public void measure(final DefinitionGroup definitionGroup, final ViewGroup viewGroup) {
        final GridGroupDefinition gridGroupDefinition = definitionGroup.getGridGroupDefinition();
        final List<View> views = definitionGroup.getViews();
        final List<GridItemDefinition> gridItemDefinitions = gridGroupDefinition.getGridItemDefinitions();
        final AdapterViewManager adapterViewManager = getAdapterViewManager();
        final int cellSpacing = mGridDefinitionLayoutManagerAttributes.getCellSpacing();
        for (int index = 0; index < views.size(); index++) {
            final View view = views.get(index);
            final GridItemDefinition gridItemDefinition = gridItemDefinitions.get(index);
            final int maxMeasureWidth = getMaxMeasuredWidth(gridGroupDefinition, gridItemDefinition, cellSpacing);
            final int maxMeasureHeight = getMaxMeasuredHeight(gridGroupDefinition, gridItemDefinition, cellSpacing);

            adapterViewManager.measureView(view, maxMeasureWidth, maxMeasureHeight);
        }
    }

    @Override
    public View getLastAdapterPositionView(final DefinitionGroup definitionGroup) {
        final List<View> views = definitionGroup.getViews();
        final int index = views.size() - 1;
        return views.get(index);
    }

    @Override
    public View getFirstAdapterPositionView(final DefinitionGroup definitionGroup) {
        final List<View> views = definitionGroup.getViews();
        return views.get(0);
    }

    @Override
    protected int getFirstAdapterPositionInCell(final int cellPosition) {
        final int gridGroupDefinitionSize = mGridGroupDefinitions.size();
        final int gridGroupDefinitionsRepeated = cellPosition / gridGroupDefinitionSize;
        final int gridGroupDefinitionPosition = cellPosition % gridGroupDefinitionSize;

        int adapterPositionOffset = 0;
        for (int index = 0; index < gridGroupDefinitionPosition; index++) {
            final GridGroupDefinition gridGroupDefinition = mGridGroupDefinitions.get(index);
            final int numberOfItems = gridGroupDefinition.getNumberOfItems();
            adapterPositionOffset += numberOfItems;
        }

        final int adapterPosition = gridGroupDefinitionsRepeated * mNumberOfGridItemsPerRepetition + adapterPositionOffset;

        return adapterPosition;
    }

    @Override
    protected int getDrawPosition(final List<DefinitionGroup> definitionGroups, final int drawCellPosition) {

        int drawPosition = 0;

        for (int index = 0; index < drawCellPosition; index++) {
            final DefinitionGroup definitionGroup = definitionGroups.get(index);
            final int numberOfItems = definitionGroup.getNumberOfItems();
            drawPosition += numberOfItems;
        }

        return drawPosition;
    }

    @Override
    protected int getLastAdapterPositionInCell(final int cellPosition) {
        final int gridGroupDefinitionSize = mGridGroupDefinitions.size();
        final int gridGroupDefinitionsRepeated = cellPosition / gridGroupDefinitionSize;
        final int gridGroupDefinitionPosition = cellPosition % gridGroupDefinitionSize;

        int adapterPositionOffset = 0;
        for (int index = 0; index <= gridGroupDefinitionPosition; index++) {
            final GridGroupDefinition gridGroupDefinition = mGridGroupDefinitions.get(index);
            final int numberOfItems = gridGroupDefinition.getNumberOfItems();
            adapterPositionOffset += numberOfItems;
        }

        final int adapterPosition = gridGroupDefinitionsRepeated * mNumberOfGridItemsPerRepetition + adapterPositionOffset - 1;

        return adapterPosition;
    }

    @Override
    protected int getCellCount() {
        final AdapterViewManager adapterViewManager = getAdapterViewManager();
        final int adapterCount = adapterViewManager.getAdapterCount();
        if (adapterCount <= 0){
            return -1;
        }

        final int endPosition = Math.max(0, adapterCount - 1);
        return getCellPosition(endPosition) + 1;
    }

    @Override
    protected int getCellPosition(final int adapterPosition) {
        final int adapterPositionOffset = adapterPosition % mNumberOfGridItemsPerRepetition;
        final int numberOfRepetitions = adapterPosition / mNumberOfGridItemsPerRepetition;

        int position = 0;
        int cellOffsetCounter = 0;
        int cellOffset = 0;

        while (position < adapterPositionOffset) {
            final GridGroupDefinition gridGroupDefinition = mGridGroupDefinitions.get(cellOffsetCounter);
            final int numberOfItems = gridGroupDefinition.getNumberOfItems();
            position += numberOfItems;
            cellOffset = cellOffsetCounter++;
        }

        final int numberOfCellsRepeated = numberOfRepetitions * mGridGroupDefinitions.size();
        final int cellPosition = numberOfCellsRepeated + cellOffset;
        return cellPosition;
    }

    private GridGroupDefinition getGridGroupDefinition(final int adapterPosition) {
        int views = 0;

        int index = 0;
        while (views < adapterPosition) {
            final GridGroupDefinition gridGroupDefinition = mGridGroupDefinitions.get(index);
            final int viewsInCell = gridGroupDefinition.getNumberOfItems();
            views += viewsInCell;
            index = (index + 1) % mGridGroupDefinitions.size();
        }
        final GridGroupDefinition gridGroupDefinition = mGridGroupDefinitions.get(index);
        return gridGroupDefinition;
    }
}
