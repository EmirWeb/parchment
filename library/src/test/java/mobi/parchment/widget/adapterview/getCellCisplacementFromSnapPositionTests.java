package mobi.parchment.widget.adapterview;

import android.view.View;
import android.view.ViewGroup;

import mobi.parchment.widget.adapterview.snapposition.CenterSnapPosition;
import mobi.parchment.widget.adapterview.snapposition.EndSnapPosition;
import mobi.parchment.widget.adapterview.snapposition.EndWithCellSpacingSnapPosition;
import mobi.parchment.widget.adapterview.snapposition.OnScreenSnapPosition;
import mobi.parchment.widget.adapterview.snapposition.OnScreenWithCellSpacingSnapPosition;
import mobi.parchment.widget.adapterview.snapposition.StartSnapPosition;
import mobi.parchment.widget.adapterview.snapposition.StartWithCellSpacingSnapPosition;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Emir Hasanbegovic on 2014-03-18.
 */

@RunWith(RobolectricTestRunner.class)
public class getCellCisplacementFromSnapPositionTests {
    private LayoutManager<Cell> mLayoutManager;

    @Before
    public void setup() {
        final ViewGroup viewGroup = new ViewGroup(Robolectric.application.getApplicationContext()) {
            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {

            }
        };
        AdapterViewManager adapterViewManager = new AdapterViewManager();
        LayoutManagerAttributes layoutManagerAttributes = new LayoutManagerAttributes(false, true, false, 0, SnapPosition.center, 10, true, true, true);
        mLayoutManager = new ShadowLayoutManager(viewGroup, null, adapterViewManager, layoutManagerAttributes);

    }

    @Test
    public void testCenterDisplacementNegative() {
        final CenterSnapPosition<Cell> snapPosition = new CenterSnapPosition();
        final Cell cell = new Cell(50, 100);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(-25);
    }

    @Test
    public void testCenterDisplacementIs0() {
        final CenterSnapPosition<Cell> snapPosition = new CenterSnapPosition();
        final Cell cell = new Cell(25, 75);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(0);
    }

    @Test
    public void testCenterDisplacementIsPositive() {
        final CenterSnapPosition<Cell> snapPosition = new CenterSnapPosition();
        final Cell cell = new Cell(0, 50);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(25);
    }


    @Test
    public void testStartDisplacementIsNegative() {
        final StartSnapPosition<Cell> snapPosition = new StartSnapPosition();
        final Cell cell = new Cell(75, 100);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(-75);
    }

    @Test
    public void testStartDisplacementIs0() {
        final StartSnapPosition<Cell> snapPosition = new StartSnapPosition();
        final Cell cell = new Cell(0, 25);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(0);
    }

    @Test
    public void testStartDisplacementIsPositive() {
        final StartSnapPosition<Cell> snapPosition = new StartSnapPosition();
        final Cell cell = new Cell(-10, 15);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(10);
    }

    @Test
    public void testStartWithCellSpacingDisplacementIsNegative() {
        final StartWithCellSpacingSnapPosition<Cell> snapPosition = new StartWithCellSpacingSnapPosition();
        final Cell cell = new Cell(75, 100);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(-65);
    }

    @Test
    public void testStartWithCellSpacingDisplacementIs0() {
        final StartWithCellSpacingSnapPosition<Cell> snapPosition = new StartWithCellSpacingSnapPosition();
        final Cell cell = new Cell(10, 35);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(0);
    }

    @Test
    public void testStartWithCellSpacingDisplacementIsPositive() {
        final StartWithCellSpacingSnapPosition<Cell> snapPosition = new StartWithCellSpacingSnapPosition();
        final Cell cell = new Cell(0, 25);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(10);
    }

    @Test
    public void testEndDisplacementIsNegative() {
        final EndSnapPosition<Cell> snapPosition = new EndSnapPosition();
        final Cell cell = new Cell(85,110);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(-10);
    }

    @Test
    public void testEndDisplacementIs0() {
        final EndSnapPosition<Cell> snapPosition = new EndSnapPosition();
        final Cell cell = new Cell(75,100);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(0);
    }

    @Test
    public void testEndDisplacementIsPositive() {
        final EndSnapPosition<Cell> snapPosition = new EndSnapPosition();
        final Cell cell = new Cell(65,90);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(10);
    }

    @Test
    public void testEndWithCellSpacingDisplacementIsNegative() {
        final EndWithCellSpacingSnapPosition<Cell> snapPosition = new EndWithCellSpacingSnapPosition();
        final Cell cell = new Cell(75,100);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(-10);
    }

    @Test
    public void testEndWithCellSpacingDisplacementIs0() {
        final EndWithCellSpacingSnapPosition<Cell> snapPosition = new EndWithCellSpacingSnapPosition();
        final Cell cell = new Cell(65,90);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(0);
    }

    @Test
    public void testEndWithCellSpacingDisplacementIsPositive() {
        final EndWithCellSpacingSnapPosition<Cell> snapPosition = new EndWithCellSpacingSnapPosition();
        final Cell cell = new Cell(55,80);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(10);
    }

    @Test
    public void testOnScreenWithCellSpacingDisplacementIsNegative() {
        final OnScreenWithCellSpacingSnapPosition<Cell> snapPosition = new OnScreenWithCellSpacingSnapPosition();
        final Cell cell = new Cell(90, 100);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(-10);
    }

    @Test
    public void testOnScreenWithCellSpacingDisplacementIs0() {
        final OnScreenWithCellSpacingSnapPosition<Cell> snapPosition = new OnScreenWithCellSpacingSnapPosition();
        final Cell cell = new Cell(65, 90);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(0);
    }

    @Test
    public void testOnScreenWithCellSpacingDisplacementIsPositive() {
        final OnScreenWithCellSpacingSnapPosition<Cell> snapPosition = new OnScreenWithCellSpacingSnapPosition();
        final Cell cell = new Cell(-10,10);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(20);
    }

    @Test
    public void testOnScreenDisplacementIsNegative() {
        final OnScreenSnapPosition<Cell> snapPosition = new OnScreenSnapPosition();
        final Cell cell = new Cell(90, 110);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(-10);
    }

    @Test
    public void testOnScreenDisplacementIs0() {
        final OnScreenSnapPosition<Cell> snapPosition = new OnScreenSnapPosition();
        Cell cell = new Cell(65, 100);
        int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(0);

        cell = new Cell(0, 10);
        displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(0);
    }

    @Test
    public void testOnScreenDisplacementIsPositive() {
        final OnScreenSnapPosition<Cell> snapPosition = new OnScreenSnapPosition();
        final Cell cell = new Cell(-10,10);
        final int displacement = snapPosition.getCellDisplacementFromSnapPosition(mLayoutManager, 100, cell, 10);
        assertThat(displacement).isEqualTo(10);
    }

    private final class Cell {
        public final int mCenter;
        public final int mStart;
        public final int mEnd;

        public Cell(final int start, final int end) {
            mStart = start;
            mEnd = end;
            mCenter = (start + end) / 2;
        }
    }

    private final class ShadowLayoutManager extends LayoutManager<Cell> {

        public ShadowLayoutManager(ViewGroup viewGroup, OnSelectedListener onSelectedListener, AdapterViewManager adapterViewManager, LayoutManagerAttributes layoutManagerAttributes) {
            super(viewGroup, onSelectedListener, adapterViewManager, layoutManagerAttributes);
        }

        @Override
        public View getLastView(Cell cell) {
            return null;
        }

        @Override
        public View getView(Cell cell) {
            return null;
        }

        @Override
        public View getFirstView(Cell cell) {
            return null;
        }

        @Override
        public int getCellStart(Cell cell) {
            return cell.mStart;
        }

        @Override
        public int getCellEnd(Cell cell) {
            return cell.mEnd;
        }

        @Override
        public int getCellSize(Cell cell) {
            return 0;
        }

        @Override
        public List<View> getViews(Cell cell) {
            return null;
        }

        @Override
        public Cell getCell(int adapterPosition) {
            return null;
        }

        @Override
        public void layoutCell(Cell cell, int cellStart, int cellEnd, int firstAdapterPositionInCell, int breadth, int cellSpacing) {

        }

        @Override
        public void measure(Cell cell, ViewGroup viewGroup) {

        }

        public int getCellCenter(Cell cell) {
            return cell.mCenter;
        }

        @Override
        public View getLastAdapterPositionView(Cell cell) {
            return null;
        }

        @Override
        public View getFirstAdapterPositionView(Cell cell) {
            return null;
        }

        @Override
        protected int getCellCount() {
            return 0;
        }

        @Override
        protected int getMaxMeasureHeight(int position) {
            return 0;
        }

        @Override
        protected int getMaxMeasureWidth(int position) {
            return 0;
        }

        @Override
        protected int getCellPosition(int adapterPosition) {
            return 0;
        }

        @Override
        protected int getFirstAdapterPositionInCell(int cellPosition) {
            return 0;
        }

        @Override
        protected int getDrawPosition(List<Cell> cells, int drawCellPosition) {
            return 0;
        }

        @Override
        protected int getLastAdapterPositionInCell(int cellPosition) {
            return 0;
        }
    }
}
