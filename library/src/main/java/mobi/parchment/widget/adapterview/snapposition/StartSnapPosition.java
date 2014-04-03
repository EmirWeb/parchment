package mobi.parchment.widget.adapterview.snapposition;

import android.view.View;

import mobi.parchment.widget.adapterview.LayoutManager;
import mobi.parchment.widget.adapterview.Move;
import mobi.parchment.widget.adapterview.ScrollDirectionManager;

import java.util.List;

/**
 * Created by Emir Hasanbegovic on 2014-03-11.
 */
public class StartSnapPosition<Cell> implements SnapPositionInterface<Cell> {

    @Override
    public int getDrawLimitMoveForwardOverDrawAdjust(final LayoutManager<Cell> layoutManager, final List<Cell> cells, final int size, final Cell cell) {
        final int cellSize = layoutManager.getCellSize(cell);
        return cellSize;
    }

    @Override
    public int getDrawLimitMoveBackwardOverDrawAdjust(LayoutManager<Cell> layoutManager, List<Cell> cells, int size, Cell cell) {
        return 0;
    }

    @Override
    public int getCellDisplacementFromSnapPosition(LayoutManager<Cell> layoutManager, int size, Cell cell) {
        final int currentCellStart = layoutManager.getCellStart(cell);
        final int displacement = -currentCellStart;
        return displacement;
    }

    @Override
    public int getCellDistanceFromSnapPosition(LayoutManager<Cell> layoutManager, int size, Cell cell) {
        final int displacement = getCellDisplacementFromSnapPosition(layoutManager, size, cell);
        return Math.abs(displacement);
    }

    @Override
    public int getSnapToPixelDistance(LayoutManager<Cell> layoutManager, ScrollDirectionManager scrollDirectionManager, int size, View view) {
        final int startPixel = scrollDirectionManager.getViewStart(view);

        return -startPixel;
    }

    @Override
    public int getRedrawOffset(final ScrollDirectionManager scrollDirectionManager, final View incomingView, final View outgoingView) {
        final int outgoingViewStart = scrollDirectionManager.getViewStart(outgoingView);
        return outgoingViewStart;
    }

    @Override
    public int getAbsoluteSnapPosition(final int size,  final int cellSize, final Move move) {
        return 0;
    }
}
