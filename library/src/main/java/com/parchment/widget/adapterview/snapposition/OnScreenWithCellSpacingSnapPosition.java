package com.parchment.widget.adapterview.snapposition;

import android.view.View;

import com.parchment.widget.adapterview.LayoutManager;
import com.parchment.widget.adapterview.ScrollDirectionManager;

import java.util.List;

/**
 * Created by Emir Hasanbegovic on 2014-03-11.
 */
public class OnScreenWithCellSpacingSnapPosition<Cell> implements SnapPositionInterface<Cell> {

    @Override
    public int getDrawLimitMoveForwardOverDrawAdjust(final LayoutManager<Cell> layoutManager, final List<Cell> cells, final int size, final Cell cell, final int cellSpacing) {
        final int cellSize = layoutManager.getCellSize(cell);
        final int drawLimit = cellSize + cellSpacing;

        final int lastCellIndex = cells.size() - 1;
        final Cell lastCell = cells.get(lastCellIndex);
        final View lastView = layoutManager.getLastAdapterPositionView(lastCell);

        final int lastViewPosition = layoutManager.getPosition(lastView);
        final boolean isLastItemOnScreen = lastViewPosition == layoutManager.getAdapterCount() - 1;
        if (isLastItemOnScreen) {
            final int drawLimitAdjustment = layoutManager.getCellSizeTotal() - (cellSize + cellSpacing);
            return Math.max(drawLimit, size - drawLimitAdjustment);
        }

        return drawLimit;
    }

    @Override
    public int getDrawLimitMoveBackwardOverDrawAdjust(LayoutManager<Cell> layoutManager, List<Cell> cells, int size, Cell cell, int cellSpacing) {
        final int cellSize = layoutManager.getCellSize(cell);
        final int drawLimit = size - cellSize - cellSpacing;

        final Cell firstCell = cells.get(0);
        final View firstView = layoutManager.getFirstAdapterPositionView(firstCell);

        final int firstViewPosition = layoutManager.getPosition(firstView);
        final boolean isFirstItemOnScreen = firstViewPosition == 0;
        if (isFirstItemOnScreen) {
            return Math.min(drawLimit, layoutManager.getCellSizeTotal() - cellSpacing - cellSize);
        }

        return drawLimit;
    }

    @Override
    public int getCellDistanceFromSnapPosition(LayoutManager<Cell> layoutManager, int size, Cell cell, int cellSpacing) {
        return 0;
    }

    @Override
    public int getSnapToPixelDistance(LayoutManager<Cell> layoutManager, ScrollDirectionManager scrollDirectionManager, int size, View view, int cellSpacing) {
        final int startPixel = scrollDirectionManager.getViewStart(view);
        final int endPixel = scrollDirectionManager.getViewEnd(view);

        if (startPixel - cellSpacing <= 0) {
            return -startPixel + cellSpacing;
        } else if (endPixel + cellSpacing > size) {
            return size - endPixel - cellSpacing;
        }

        return 0;
    }

    @Override
    public int getRedrawOffset(final ScrollDirectionManager scrollDirectionManager, final View incomingView, final View outgoingView, final int cellSpacing) {
        final int outgoingViewStart = scrollDirectionManager.getViewStart(outgoingView);
        return outgoingViewStart - cellSpacing;
    }
}
