package mobi.parchment.widget.adapterview;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

/**
 * Created by Emir Hasanbegovic
 */
public class ChildTouchGestureListener extends AdapterAnimator {

	private final OnClickListener mOnClickListener;
	private OnLongClickListener mOnLongClickListener;
	private final int MAX_FOCUS_DISPLACEMENT = 300;
	private View mPressedView;

	public ChildTouchGestureListener(final ViewGroup viewGroup, final boolean isViewPager, final boolean isVertical, final OnClickListener onClickListener, final OnLongClickListener onLongClickListener, final LayoutManagerBridge layoutManagerBridge) {
		super(viewGroup, isViewPager, isVertical, layoutManagerBridge);
		mOnClickListener = onClickListener;
		mOnLongClickListener = onLongClickListener;
	}
	
	@Override
	public void onLongPress(final MotionEvent motionEvent) {
		if (mPressedView != null) {
			mPressedView.setPressed(false);
			onItemLongClick(mPressedView);
			super.onLongPress(motionEvent);
		}
		mPressedView = null;
		super.onLongPress(motionEvent);
	}

	@Override
	public boolean onScroll(final MotionEvent e1, final MotionEvent e2, final float distanceX, final float distanceY) {
		if (e1 != null && e2 != null && Math.abs(e1.getX() - e2.getX()) > MAX_FOCUS_DISPLACEMENT) {
			if (mPressedView != null) {
				mPressedView.setPressed(false);
				mPressedView = null;
			}
		}
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	@Override
	public boolean onSingleTapUp(MotionEvent motionEvent) {
		if (mPressedView != null) {
			mPressedView.setPressed(false);
			onItemClick(mPressedView);
			return super.onSingleTapUp(motionEvent, mPressedView);
		}
		mPressedView = null;
		return super.onSingleTapUp(motionEvent);
	}

	@Override
	public boolean onDown(final MotionEvent event) {

		mPressedView = null;

		final ViewGroup viewGroup = getViewGroup();

		final int pointerCount = event.getPointerCount();

		for (int p = 0; p < pointerCount; p++) {
			final double touchX = event.getX(p);
			final double touchY = event.getY(p);

			for (int i = viewGroup.getChildCount() - 1; i >= 0; i--) {
				final View child = viewGroup.getChildAt(i);
				final float childLeft = child.getLeft();
				final float childTop = child.getTop();
				final float childRight = child.getWidth() + childLeft;
				final float childBottom = child.getHeight() + childTop;

				final boolean xFits = touchX >= childLeft && touchX <= childRight;
				final boolean yFits = touchY >= childTop && touchY <= childBottom;

				if (xFits && yFits) {
					mPressedView = child;
					child.setPressed(true);
					return super.onDown(event);
				}
			}
		}

		return super.onDown(event);
	}

	private void onItemLongClick(final View view) {
		if (mOnLongClickListener== null)
			return;

		mOnLongClickListener.onLongClick(view);
		
	}

	
	private void onItemClick(final View view) {
		if (mOnClickListener == null)
			return;

		mOnClickListener.onClick(view);
	}

	public void onCancel() {
		stopTouch();
	}

	@Override
	public void onUp() {
		stopTouch();
		super.onUp();
	}

	private void stopTouch() {
		if (mPressedView != null)
			mPressedView.setPressed(false);
		mPressedView = null;
	}

}
