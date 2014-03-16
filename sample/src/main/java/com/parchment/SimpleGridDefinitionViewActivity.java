package com.parchment;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.parchment.widget.adapterview.griddefinitionview.GridDefinitionView;
import com.parchment.widget.adapterview.griddefinitionview.GridItemDefinition;

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
        gridItemDefinitions.add(new GridItemDefinition(0, 0, 1, 1));
        gridItemDefinitions.add(new GridItemDefinition(0, 1, 1, 1));
        mGridDefinitionView.addGridGroupDefinition(gridItemDefinitions);

        gridItemDefinitions.clear();
        gridItemDefinitions.add(new GridItemDefinition(0, 0, 1, 2));
        mGridDefinitionView.addGridGroupDefinition(gridItemDefinitions);

        gridItemDefinitions.clear();
        gridItemDefinitions.add(new GridItemDefinition(0, 0, 1, 1));
        gridItemDefinitions.add(new GridItemDefinition(0, 1, 1, 1));
        gridItemDefinitions.add(new GridItemDefinition(1, 0, 1, 1));
        gridItemDefinitions.add(new GridItemDefinition(1, 1, 1, 1));
        mGridDefinitionView.addGridGroupDefinition(gridItemDefinitions);

        mGridDefinitionView.setAdapter(getCountBaseAdapter());
    }

    @Override
    public AdapterView<?> getAdapterView() {
        return mGridDefinitionView;
    }
}
