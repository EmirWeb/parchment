package com.parchment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CountBaseAdapter extends BaseAdapter {
	private static final int[] VIEW_TYPES = new int[] {
		R.layout.item_simple,
		R.layout.item_simple_larger
	};
	
	private int mCount = 20;
	
	
	final String[] strings = new String[] {
			"Hello world!",
			"This should be a two liners, lets see what happens.",
			""
	};

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position >= mCount || position < 0)
			throw new IndexOutOfBoundsException();

		if (convertView == null) {
			final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
			convertView = layoutInflater.inflate(VIEW_TYPES[getItemViewType(position)], null);
		}

		final TextView button = ((TextView) convertView.findViewById(R.id.item_simple_button));
		button.setText((String) getItem(position) + strings[position % strings.length]);

		return convertView;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	@Override
	public int getItemViewType(int position) {
		if (position % 9 == 0 || position % 9 == 7)
			return 1;
		else
			return 0;
	}
	
	@Override
	public long getItemId(int position) {
		if (position >= mCount || position < 0)
			throw new IndexOutOfBoundsException();
		return position;
	}

    @Override
	public Object getItem(int position) {
		if (position >= mCount || position < 0)
			throw new IndexOutOfBoundsException();
		return Integer.toString(position);
	}

	@Override
	public int getCount() {
		return mCount;
	}

	private void setCount(final int count) {
		mCount = Math.max(0, count);
		notifyDataSetChanged();
	}

	public void add() {
		setCount(mCount + 8);
	}

	public void remove() {
		setCount(mCount - 8);
	}

	public void addOne() {
		setCount(mCount + 1);
	}

	public void removeOne() {
		setCount(mCount - 1);
	}

}
