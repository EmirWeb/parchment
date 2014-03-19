package mobi.parchment.widget.adapterview.snapposition;

import android.view.View;

import mobi.parchment.widget.adapterview.LayoutManager;
import mobi.parchment.widget.adapterview.Move;
import mobi.parchment.widget.adapterview.ScrollDirectionManager;

import java.util.List;

/**
 * Created by Emir Hasanbegovic on 2014-03-11.
 */
public interface SnapPositionInterface<Cell> {

    public int getDrawLimitMoveForwardOverDrawAdjust(final LayoutManager<Cell> layoutManager, final List<Cell> cells, final int size, final Cell cell, final int cellSpacing);

    public int getDrawLimitMoveBackwardOverDrawAdjust(final LayoutManager<Cell> layoutManager, final List<Cell> cells, final int size, final Cell cell, final int cellSpacing);

    public int getCellDisplacementFromSnapPosition(final LayoutManager<Cell> layoutManager, final int size, final Cell cell, final int cellSpacing);

    public int getCellDistanceFromSnapPosition(final LayoutManager<Cell> layoutManager, final int size, final Cell cell, final int cellSpacing);

    public int getSnapToPixelDistance(final LayoutManager<Cell> layoutManager, final ScrollDirectionManager scrollDirectionManager, final int size, final View view, final int cellSpacing);

    public int getRedrawOffset(final ScrollDirectionManager scrollDirectionManager, final View incomingView, final View outgoingView, final int cellSpacing);

    public int getAbsoluteSnapPosition(final int size, final int cellSpacing, final int cellSize, final Move move);
}
