package mobi.parchment;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import mobi.parchment.widget.adapterview.griddefinitionview.GridDefinitionView;
import mobi.parchment.widget.adapterview.griddefinitionview.GridItemDefinition;

import java.util.ArrayList;
import java.util.List;

public class SimpleGridDefinitionViewActivity extends BaseActivity {

    private GridDefinitionView<BaseAdapter> mGridDefinitionView;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_grid_definition_view);

        mGridDefinitionView = (GridDefinitionView<BaseAdapter>) findViewById(R.id.parchment_view);

        final List<GridItemDefinition> gridItemDefinitions = new ArrayList<GridItemDefinition>();

        gridItemDefinitions.add(new GridItemDefinition(0, 0, 6, 4));
        mGridDefinitionView.addGridGroupDefinition(gridItemDefinitions);
        gridItemDefinitions.clear();

        gridItemDefinitions.add(new GridItemDefinition(0, 0, 2, 2));
        gridItemDefinitions.add(new GridItemDefinition(2, 0, 2, 2));
        gridItemDefinitions.add(new GridItemDefinition(4, 0, 2, 3));
        gridItemDefinitions.add(new GridItemDefinition(0, 2, 4, 4));
        gridItemDefinitions.add(new GridItemDefinition(4, 3, 2, 3));
        mGridDefinitionView.addGridGroupDefinition(gridItemDefinitions);
        gridItemDefinitions.clear();

        gridItemDefinitions.add(new GridItemDefinition(0, 0, 2, 3));
        gridItemDefinitions.add(new GridItemDefinition(2, 0, 4, 4));
        gridItemDefinitions.add(new GridItemDefinition(0, 3, 2, 3));
        gridItemDefinitions.add(new GridItemDefinition(2, 4, 2, 2));
        gridItemDefinitions.add(new GridItemDefinition(4, 4, 2, 2));
        mGridDefinitionView.addGridGroupDefinition(gridItemDefinitions);

        mGridDefinitionView.setAdapter(getProductsAdapter());
    }

    @Override
    public AdapterView<?> getAdapterView() {
        return mGridDefinitionView;
    }
}
