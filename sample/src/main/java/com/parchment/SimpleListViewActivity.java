package com.parchment;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.parchment.widget.adapterview.listview.ListView;

public class SimpleListViewActivity extends BaseActivity {

	private ListView<BaseAdapter> mHorizontalListView;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_horizontal);
		
		mHorizontalListView = (ListView<BaseAdapter>) findViewById(R.id.parchment_view);
		mHorizontalListView.setAdapter(getCountBaseAdapter());
	}

    @Override
    public AdapterView<?> getAdapterView() {
        return mHorizontalListView;
    }
}
