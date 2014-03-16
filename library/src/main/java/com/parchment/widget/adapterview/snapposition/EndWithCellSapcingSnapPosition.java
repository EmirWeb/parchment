package com.parchment.widget.adapterview.snapposition;

import android.view.View;

import com.parchment.widget.adapterview.LayoutManager;
import com.parchment.widget.adapterview.ScrollDirectionManager;

import java.util.List;

/**
 * Created by Emir Hasanbegovic on 2014-03-11.
 */
public class EndWithCellSapcingSnapPosition<Cell> implements SnapPositionInterface<Cell> {

    @Override
    public int getDrawLimitMoveForwardOverDrawAdjust(final LayoutManager<Cell> layoutManager, final List<Cell> cells, final int size, final Cell cell, final int cellSpacing) {
        return size - cellSpacing;
    }

    @Override
    public int getDrawLimitMoveBackwardOverDrawAdjust(LayoutManager<Cell> layoutManager, List<Cell> cells, int size, Cell cell, int cellSpacing) {
        final int cellSize = layoutManager.getCellSize(cell);
        return size - cellSize - cellSpacing;
    }

    @Override
    public int getCellDistanceFromSnapPosition(LayoutManager<Cell> layoutManager, int size, Cell cell, int cellSpacing) {
        final int currentCellCenter = layoutManager.getCellCenter(cell);
        return Math.abs(currentCellCenter - size + cellSpacing);
    }

    @Override
    public int getSnapToPixelDistance(LayoutManager<Cell> layoutManager, ScrollDirectionManager scrollDirectionManager, int size, View view, int cellSpacing) {
        final int startPixel = scrollDirectionManager.getViewStart(view);
        final int viewSize = scrollDirectionManager.getViewSize(view);

        return size - viewSize - startPixel - cellSpacing;
    }

    @Override
    public int getRedrawOffset(final ScrollDirectionManager scrollDirectionManager, final View incomingView, final View outgoingView, final int cellSpacing) {
        final int outgoingViewStart = scrollDirectionManager.getViewStart(outgoingView);
        final int incomingViewSize = scrollDirectionManager.getViewSize(incomingView);
        final int outgoingViewSize = scrollDirectionManager.getViewSize(outgoingView);

        return outgoingViewStart + outgoingViewSize - incomingViewSize - cellSpacing;
    }
}