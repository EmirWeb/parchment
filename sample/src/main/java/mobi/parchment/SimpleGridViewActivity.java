package mobi.parchment;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import mobi.parchment.widget.adapterview.gridview.GridView;

public class SimpleGridViewActivity extends BaseActivity {

	private GridView<BaseAdapter> mGridView;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_gridview);
		
		mGridView = (GridView<BaseAdapter>) findViewById(R.id.parchment_view);
		mGridView.setAdapter(getProductsAdapter());
	}

    @Override
    public AdapterView<?> getAdapterView() {
        return mGridView;
    }

}
