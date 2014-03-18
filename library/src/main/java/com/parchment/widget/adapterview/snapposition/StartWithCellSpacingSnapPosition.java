package com.parchment.widget.adapterview.snapposition;

import android.view.View;

import com.parchment.widget.adapterview.LayoutManager;
import com.parchment.widget.adapterview.ScrollDirectionManager;

import java.util.List;

/**
 * Created by Emir Hasanbegovic on 2014-03-11.
 */
public class StartWithCellSpacingSnapPosition<Cell> implements SnapPositionInterface<Cell>{

    @Override
    public int getDrawLimitMoveForwardOverDrawAdjust(final LayoutManager<Cell> layoutManager, final List<Cell> cells, final int size, final Cell cell, final int cellSpacing) {
        final int cellSize = layoutManager.getCellSize(cell);
        return cellSize + cellSpacing;
    }

    @Override
    public int getDrawLimitMoveBackwardOverDrawAdjust(LayoutManager<Cell> layoutManager, List<Cell> cells, int size, Cell cell, int cellSpacing) {
        return cellSpacing;
    }

    @Override
    public int getCellDisplacementFromSnapPosition(LayoutManager<Cell> layoutManager, int size, Cell cell, int cellSpacing) {
        final int currentCellStart = layoutManager.getCellStart(cell);
        final int displacement = - currentCellStart + cellSpacing;
        return displacement;
    }

    @Override
    public int getCellDistanceFromSnapPosition(LayoutManager<Cell> layoutManager, int size, Cell cell, int cellSpacing) {
        final int displacement = getCellDisplacementFromSnapPosition(layoutManager, size, cell, cellSpacing);
        return Math.abs(displacement);
    }

    @Override
    public int getSnapToPixelDistance(final LayoutManager<Cell> layoutManager, final ScrollDirectionManager scrollDirectionManager, final int size, final View view, final int cellSpacing) {
        final int startPixel = scrollDirectionManager.getViewStart(view);
        return cellSpacing - startPixel;
    }

    @Override
    public int getRedrawOffset(final ScrollDirectionManager scrollDirectionManager, final View incomingView, final View outgoingView, final int cellSpacing) {
        final int outgoingViewStart = scrollDirectionManager.getViewStart(outgoingView);
        return outgoingViewStart - cellSpacing;
    }
}
