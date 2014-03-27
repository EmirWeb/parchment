package mobi.parchment;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import mobi.parchment.widget.adapterview.listview.ListView;

public class SimpleViewPagerActivity extends BaseActivity{

	private ListView<BaseAdapter> mViewPager;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_view_pager);

		mViewPager = (ListView<BaseAdapter>) findViewById(R.id.parchment_view);
		mViewPager.setAdapter(getProductsAdapter());
	}

    @Override
    public AdapterView<?> getAdapterView() {
        return mViewPager;
    }

}
