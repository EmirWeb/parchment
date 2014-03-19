package mobi.parchment.widget.adapterview;

import java.util.HashMap;

import android.view.View;

/**
 * Created by Emir Hasanbegovic
 */
public class SelectedPositionManager {
	private final OnSelectedListener mOnSelectedListener;
	
	private int mSelectedPosition;
	private boolean mItemSelectedListenerCalled = false;
	
	public SelectedPositionManager(final OnSelectedListener onSelectedListener) {
		mOnSelectedListener = onSelectedListener;
		mSelectedPosition = -1;
	}
	
	public boolean setSelectedPosition(final int position) {
		if (mSelectedPosition == position)
			return false;
		mSelectedPosition = position;
		mItemSelectedListenerCalled = false;
		return true;
	}
	
	public void setSelectNothing() {
		if (mOnSelectedListener != null)
			mOnSelectedListener.onSelected(null);
		mSelectedPosition = -1;
	}
	
	public void onViewsDrawn(final HashMap<View, Integer> positions) {
		if (mItemSelectedListenerCalled)
			return;
		
		View selectedView = null;
		for (final View view : positions.keySet())
			if (mSelectedPosition == positions.get(view)) {
				selectedView = view;
				break;
			}
		
		if (selectedView == null)
			return;
		
		if (mOnSelectedListener != null)
			mOnSelectedListener.onSelected(selectedView);
		mItemSelectedListenerCalled = true;
	}
	
	public int getSelectedPosition() {
		return mSelectedPosition;
	}
}
