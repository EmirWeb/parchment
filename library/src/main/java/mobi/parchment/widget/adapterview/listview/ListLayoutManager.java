package mobi.parchment.widget.adapterview.listview;

import android.view.View;
import android.view.ViewGroup;

import mobi.parchment.widget.adapterview.AdapterViewManager;
import mobi.parchment.widget.adapterview.LayoutManager;
import mobi.parchment.widget.adapterview.LayoutManagerAttributes;
import mobi.parchment.widget.adapterview.OnSelectedListener;
import mobi.parchment.widget.adapterview.utilities.ViewGroupUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emir Hasanbegovic on 2014-02-28.
 */
public class ListLayoutManager extends LayoutManager<View> {

    public ListLayoutManager(final ViewGroup viewGroup, final OnSelectedListener onSelectedListener, final AdapterViewManager adapterViewManager, final LayoutManagerAttributes attributes) {
        super(viewGroup, onSelectedListener, adapterViewManager, attributes);
    }

    @Override
    public void measure(final View view, final ViewGroup viewGroup ) {
        final int widthMeasureSpec = getWidthMeasureSpec();
        final int heightMeasureSpec = getHeightMeasureSpec();
        mAdapterViewManager.measureView(viewGroup, view, widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public View getLastAdapterPositionView(View view) {
        return view;
    }

    @Override
    public View getFirstAdapterPositionView(View view) {
        return view;
    }

    @Override
    protected int getFirstAdapterPositionInCell(int cellPosition) {
        return cellPosition;
    }

    @Override
    protected int getDrawPosition(List<View> views, int drawCellPosition) {
        return drawCellPosition;
    }

    @Override
    protected int getLastAdapterPositionInCell(final int cellPosition) {
        return cellPosition;
    }

    @Override
    protected int getCellCount() {
        final AdapterViewManager adapterViewManager = getAdapterViewManager();
        final int adapterCount = adapterViewManager.getAdapterCount();
        return adapterCount;
    }

    @Override
    protected int getMaxMeasureHeight(int position) {
        return getMaxMeasureHeight();
    }

    private int getMaxMeasureHeight() {
        final ViewGroup viewGroup = getViewGroup();
        final int viewGroupMeasuredHeight = ViewGroupUtilities.getViewGroupMeasuredHeightPadding(viewGroup);
        final int maxHeight = viewGroupMeasuredHeight;
        return maxHeight;
    }

    @Override
    protected int getMaxMeasureWidth(int position) {
        return getMaxMeasureWidth();
    }

    private int getMaxMeasureWidth() {
        final ViewGroup viewGroup = getViewGroup();
        final int viewGroupMeasuredWidth = ViewGroupUtilities.getViewGroupMeasuredWidthPadding(viewGroup);
        final int maxWidth = viewGroupMeasuredWidth;
        return maxWidth;
    }

    @Override
    protected int getCellPosition(int adapterPosition) {
        return adapterPosition;
    }

    @Override
    public int getCellStart(final View view) {
        return getViewStart(view);
    }

    @Override
    public int getCellEnd(final View view) {
        return getViewEnd(view);
    }

    @Override
    public View getView(final View view) {
        return view;
    }

    @Override
    public int getCellSize(final View view) {
        return getViewSize(view);
    }


    @Override
    public View getLastView(final View view) {
        return view;
    }

    @Override
    public View getFirstView(final View view) {
        return view;
    }

    @Override
    public List<View> getViews(final View view) {
        final List<View> views = new ArrayList<View>(1);
        views.add(view);
        return views;
    }

    @Override
    protected View getCell(int adapterPosition) {
        final AdapterViewManager adapterViewManager = getAdapterViewManager();
        final int widthMeasureSpec = getWidthMeasureSpec();
        final int heightMeasureSpec = getHeightMeasureSpec();

        final View view = adapterViewManager.getView(mViewGroup, adapterPosition, widthMeasureSpec, heightMeasureSpec);
        return view;
    }


    @Override
    public void layoutCell(final View view, final int cellStart, final int cellEnd, final int firstAdapterPositionInCell, final int breadth, final int cellSpacing) {
        final boolean isSelected = isViewSelected(firstAdapterPositionInCell);
        view.setSelected(isSelected);

        final int viewBreadth = getViewBreadth(view);
        final int viewBreadthStart = (breadth - viewBreadth) / 2;
        final int viewBreadthEnd = viewBreadthStart + viewBreadth;
        if (isVerticalScroll()) {
            view.layout(viewBreadthStart, cellStart, viewBreadthEnd, cellEnd);
        } else {
            view.layout(cellStart, viewBreadthStart, cellEnd, viewBreadthEnd);
        }
    }
}
