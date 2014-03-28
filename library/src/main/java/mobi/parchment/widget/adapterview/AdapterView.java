package mobi.parchment.widget.adapterview;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Adapter;

/**
 * Created by Emir Hasanbegovic
 */
public abstract class AdapterView<ADAPTER extends Adapter, Cell> extends android.widget.AdapterView<ADAPTER> implements OnLongClickListener, OnClickListener, OnSelectedListener, AdapterViewHandler {

    private OnItemClickListener mOnItemClickListener;
    private OnItemSelectedListener mOnItemSelectedListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private ADAPTER mAdapter;
    private AdapterViewInitializer<Cell> mAdapterViewInitializer;

    private Runnable mRequestLayout = new Runnable() {
        @Override
        public void run() {
            requestLayout();
        }
    };

    private final DataSetObserver mDataSetObserver = new DataSetObserver() {
        public void onChanged() {
            removeAllViewsInLayout();
            requestLayout();
            invalidate();
        }

        public void onInvalidated() {
            removeAllViewsInLayout();
            requestLayout();
            invalidate();
        }
    };

    public AdapterView(Context context) {
        super(context);
        initialize(context, null);
    }

    public AdapterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context, attributeSet);
    }

    public AdapterView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        initialize(context, attributeSet);
    }

    private void initialize(final Context context, final AttributeSet attributeSet) {
        mAdapterViewInitializer = getAdapterViewInitializer(context, attributeSet);
    }

    protected AdapterViewInitializer<Cell> createAdapterViewInitializer(final Context context, final boolean isViewPager, final AdapterViewManager adapterViewManager, final LayoutManager<Cell> layoutManager, final boolean isVerticalScroll) {
        final LayoutManagerBridge layoutManagerBridge = new LayoutManagerBridge(layoutManager);
        final ChildTouchGestureListener childTouchGestureListener = new ChildTouchGestureListener(this, isViewPager, isVerticalScroll, this, this, layoutManagerBridge);
        final AdapterViewGestureDetector adapterViewGestureDetector = new AdapterViewGestureDetector(context, childTouchGestureListener);

        return new AdapterViewInitializer<Cell>(childTouchGestureListener, adapterViewGestureDetector, layoutManager, adapterViewManager);
    }

    protected abstract AdapterViewInitializer<Cell> getAdapterViewInitializer(final Context context, final AttributeSet attributeSet);

    @Override
    public ADAPTER getAdapter() {
        return mAdapter;
    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(final int position) {
        final LayoutManager<Cell> layoutManager = mAdapterViewInitializer.getLayoutManager();
        layoutManager.setSelected(position, this);
        requestLayout();
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        final AdapterViewManager adapterViewManager = mAdapterViewInitializer.getAdapterViewManager();
        adapterViewManager.registerDataSetObserver(mDataSetObserver);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        final AdapterViewManager adapterViewManager = mAdapterViewInitializer.getAdapterViewManager();
        final LayoutManager<Cell> layoutManager = mAdapterViewInitializer.getLayoutManager();
        adapterViewManager.unregisterDataSetObserver(mDataSetObserver);
        layoutManager.destroy();
    }

    @Override
    public void setAdapter(ADAPTER adapter) {
        mAdapter = adapter;
        if (mAdapter != null) {
            final AdapterViewManager adapterViewManager = mAdapterViewInitializer.getAdapterViewManager();
            adapterViewManager.setAdapter(adapter);
        }

        requestLayout();
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        final LayoutManager<Cell> layoutManager = mAdapterViewInitializer.getLayoutManager();
        if (layoutManager != null) {
            layoutManager.measure(this);
        }
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(final OnItemSelectedListener onItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener;
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {

        final ChildTouchGestureListener childTouchListener = mAdapterViewInitializer.getChildTouchListener();
        final LayoutManager<Cell> layoutManager = mAdapterViewInitializer.getLayoutManager();

        childTouchListener.computeScrollOffset();
        final Animation animation = childTouchListener.getAnimation();

        final int leftSize = MeasureSpec.getSize(left);
        final int rightSize = MeasureSpec.getSize(right);
        final int topSize = MeasureSpec.getSize(top);
        final int bottomSize = MeasureSpec.getSize(bottom);

        if (layoutManager != null) {
            layoutManager.layout(this, animation, changed, leftSize, topSize, rightSize, bottomSize);
        }
        final AdapterAnimator.State state = childTouchListener.getState();
        switch (state) {
            case animatingTo:
            case flinging:
            case jumpingTo:
            case snapingTo:
                post(mRequestLayout);
                break;
            case scrolling:
            case notMoving:
            default:
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent motionEvent) {
        final boolean consumed = super.dispatchTouchEvent(motionEvent);
        final GestureDetector gestureDetector = mAdapterViewInitializer.getGestureDetector();
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public void onClick(final View view) {
        if (mOnItemClickListener == null) return;

        final LayoutManager<Cell> layoutManager = mAdapterViewInitializer.getLayoutManager();
        if (layoutManager == null || mAdapter == null) return;

        final int position = getPositionForView(view);
        final long id = mAdapter.getItemId(position);
        if (position != android.widget.AdapterView.INVALID_POSITION)
            mOnItemClickListener.onItemClick(this, view, position, id);
    }

    @Override
    public boolean addViewInAdapterView(final View child, final int index, final LayoutParams layoutParams) {
        final Rect rect = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
        final int childCount = getChildCount();
        final int drawPosition = Math.min(index, childCount);
        final boolean success = addViewInLayout(child, drawPosition, layoutParams, true);
        invalidate(rect);
        return success;
    }

    @Override
    public void removeViewInAdapterView(final View view) {
        final Rect rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        removeViewInLayout(view);
        invalidate(rect);
    }

    @Override
    public int getPositionForView(final View view) {
        final LayoutManager<Cell> layoutManager = mAdapterViewInitializer.getLayoutManager();
        if (layoutManager == null) return android.widget.AdapterView.INVALID_POSITION;

        final int position = layoutManager.getPosition(view);
        if (position == LayoutManager.INVALID_POSITION)
            return android.widget.AdapterView.INVALID_POSITION;

        return position;
    }

    @Override
    public void onSelected(final View view) {
        if (mOnItemSelectedListener == null) {
            return;
        }

        if (view != null) {
            final LayoutManager<Cell> layoutManager = mAdapterViewInitializer.getLayoutManager();
            if (layoutManager == null || mAdapter == null) return;

            final int position = getPositionForView(view);
            if (position == android.widget.AdapterView.INVALID_POSITION) return;

            final long id = mAdapter.getItemId(position);
            mOnItemSelectedListener.onItemSelected(this, view, position, id);
        } else {
            mOnItemSelectedListener.onNothingSelected(this);
        }

    }

    @Override
    public boolean onLongClick(final View view) {
        if (mOnItemLongClickListener == null || view == null) return false;

        final LayoutManager<Cell> layoutManager = mAdapterViewInitializer.getLayoutManager();
        if (layoutManager == null || mAdapter == null) return false;

        final int position = getPositionForView(view);
        if (position == android.widget.AdapterView.INVALID_POSITION) return false;

        final long id = mAdapter.getItemId(position);
        return mOnItemLongClickListener.onItemLongClick(this, view, position, id);
    }

}
