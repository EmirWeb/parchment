package mobi.parchment;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import mobi.parchment.widget.adapterview.gridpatternview.GridPatternView;
import mobi.parchment.widget.adapterview.gridpatternview.GridPatternItemDefinition;

import java.util.ArrayList;
import java.util.List;

public class SimpleGridPatternViewActivity extends BaseActivity {

    private GridPatternView<BaseAdapter> mGridPatternView;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_grid_definition_view);

        mGridPatternView = (GridPatternView<BaseAdapter>) findViewById(R.id.parchment_view);

        final List<GridPatternItemDefinition> gridPatternItemDefinitions = new ArrayList<GridPatternItemDefinition>();

        gridPatternItemDefinitions.add(new GridPatternItemDefinition(0, 0, 6, 4));
        mGridPatternView.addGridPatternGroupDefinition(gridPatternItemDefinitions);
        gridPatternItemDefinitions.clear();

        gridPatternItemDefinitions.add(new GridPatternItemDefinition(0, 0, 2, 2));
        gridPatternItemDefinitions.add(new GridPatternItemDefinition(2, 0, 2, 2));
        gridPatternItemDefinitions.add(new GridPatternItemDefinition(4, 0, 2, 3));
        gridPatternItemDefinitions.add(new GridPatternItemDefinition(0, 2, 4, 4));
        gridPatternItemDefinitions.add(new GridPatternItemDefinition(4, 3, 2, 3));
        mGridPatternView.addGridPatternGroupDefinition(gridPatternItemDefinitions);
        gridPatternItemDefinitions.clear();

        gridPatternItemDefinitions.add(new GridPatternItemDefinition(0, 0, 2, 3));
        gridPatternItemDefinitions.add(new GridPatternItemDefinition(2, 0, 4, 4));
        gridPatternItemDefinitions.add(new GridPatternItemDefinition(0, 3, 2, 3));
        gridPatternItemDefinitions.add(new GridPatternItemDefinition(2, 4, 2, 2));
        gridPatternItemDefinitions.add(new GridPatternItemDefinition(4, 4, 2, 2));
        mGridPatternView.addGridPatternGroupDefinition(gridPatternItemDefinitions);

        mGridPatternView.setAdapter(getProductsAdapter());
    }

    @Override
    public AdapterView<?> getAdapterView() {
        return mGridPatternView;
    }
}
