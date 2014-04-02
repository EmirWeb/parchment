package mobi.parchment.widget.adapterview;

import android.database.DataSetObserver;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by Emir Hasanbegovic
 * Handles the creating new views from the adapter, recycling them and , dataset updating and
 */
public class AdapterViewManager {

    private final DataSetObserverManager mDataSetObserverManager = new DataSetObserverManager();
    private final Map<View, Long> mViewIdMap = new HashMap<View, Long>();
    private final Map<View, Integer> mViewTypeMap = new HashMap<View, Integer>();
    private final Map<Long, View> mIdViewMap = new HashMap<Long, View>();
    private Adapter mAdapter;
    private List<Queue<View>> mViews = new ArrayList<Queue<View>>();

    public void recycle(final View removedView) {
        if (mAdapter.hasStableIds()) {
            final Long id = mViewIdMap.remove(removedView);
            mIdViewMap.put(id, removedView);
        }

        final Integer type = mViewTypeMap.remove(removedView);
        final Queue<View> views = mViews.get(type);
        views.add(removedView);
    }

    public View getView(final ViewGroup viewGroup, final int position, final int horizontalMeasureSpec, final int verticalMeasureSpec) {
        final int type = mAdapter.getItemViewType(position);
        final long id = mAdapter.getItemId(position);

        final View view = getView(viewGroup, position, type, id, horizontalMeasureSpec, verticalMeasureSpec);
        registerViewIsOnScreen(id, type, view);

        return view;
    }

    private void registerViewIsOnScreen(final long id, final int type, final View view) {
        if (mAdapter.hasStableIds()) {
            mViewIdMap.put(view, id);
        }
        mViewTypeMap.put(view, type);
    }

    private View getView(final ViewGroup viewGroup, final int position, final int type, final long id, final int horizontalMeasureSpec, final int verticalMeasureSpec) {
        if (mAdapter.hasStableIds() && mIdViewMap.containsKey(id)) {
            final View view = mIdViewMap.remove(id);
            final Queue<View> views = mViews.get(type);
            views.remove(view);

            return view;
        }

        final Queue<View> views = mViews.get(type);
        final View convertView = views.poll();
        final View view = mAdapter.getView(position, convertView, viewGroup);
        final boolean isRecycled =  view == convertView;
        if (!isRecycled || view.isLayoutRequested()){
            measureView(viewGroup, view, horizontalMeasureSpec, verticalMeasureSpec);
        }

        if (mAdapter.hasStableIds()) {
            remove(mIdViewMap, convertView);
            remove(mViewIdMap, id);
        }
        return view;
    }

    private <KEY, VALUE> void remove(final Map<KEY, VALUE> map, final VALUE value) {
        if (map == null || value == null) return;

        if (map.containsValue(value)) {
            final KEY key = getKeyForValue(map, value);
            if (key != null) {
                map.remove(key);
            }
        }
    }

    private <KEY, VALUE> KEY getKeyForValue(final Map<KEY, VALUE> map, final VALUE value) {
        if (value == null) {
            return null;
        }

        for (final KEY key : map.keySet()) {
            final VALUE valueToReset = map.get(key);
            if (value == valueToReset) {
                return key;
            }
        }

        return null;
    }

    public LayoutParams measureView(final ViewGroup viewGroup, final View view, final int horizontalMeasureSpec, final int verticalMeasureSpec) {
        LayoutParams layoutParams = view.getLayoutParams();

        if (layoutParams == null) {
            layoutParams = new LayoutParams(horizontalMeasureSpec, verticalMeasureSpec);
            view.setLayoutParams(layoutParams);
        }

        view.measure(horizontalMeasureSpec, verticalMeasureSpec);

        return layoutParams;
    }

    public int getAdapterCount() {
        if (mAdapter == null) return 0;
        return mAdapter.getCount();
    }

    public boolean isEmpty() {
        return getAdapterCount() == 0;
    }

    public void setAdapter(final Adapter adapter) {
        mDataSetObserverManager.setAdapter(adapter);
        mAdapter = adapter;

        final int typeCount = adapter.getViewTypeCount();

        mViews = new ArrayList<Queue<View>>(typeCount);
        for (int index = 0; index < typeCount; index++)
            mViews.add(new LinkedList<View>());

        mViewTypeMap.clear();

    }

    public void registerDataSetObserver(final DataSetObserver dataSetObserver) {
        mDataSetObserverManager.registerDataSetObserver(dataSetObserver);
    }

    public void unregisterDataSetObserver(final DataSetObserver dataSetObserver) {
        mDataSetObserverManager.unregisterDataSetObserver(dataSetObserver);
    }


}
