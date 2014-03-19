package mobi.parchment.widget.adapterview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import mobi.parchment.widget.adapterview.griddefinitionview.GridDefinitionLayoutManager;
import mobi.parchment.widget.adapterview.griddefinitionview.GridDefinitionLayoutManagerAttributes;
import mobi.parchment.widget.adapterview.griddefinitionview.GridGroupDefinition;
import mobi.parchment.widget.adapterview.griddefinitionview.GridItemDefinition;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Emir Hasanbegovic
 */
@RunWith(RobolectricTestRunner.class)
public class GridDefinitionLayoutManagerOverScrollTest {

    public static final int VIEW_GROUP_WIDTH = 230;
    public static final int VIEW_GROUP_HEIGHT = 100;
    public static final int VIEW_SIZE = 100;
    public static final int CELL_SPACING = 10;
    final MyViewGroup mViewGroup = new MyViewGroup(Robolectric.application);
    final AdapterViewManager adapterViewManager = new AdapterViewManager();
    TestAdapter mTestAdapter;
    GridDefinitionLayoutManagerAttributes attributes;
    GridDefinitionLayoutManager listLayoutManager;

    @Before
    public void setup() {
        attributes = new GridDefinitionLayoutManagerAttributes(false, true, false, 0, SnapPosition.onScreenWithCellSpacing, CELL_SPACING, true, true, true, 1f);
        listLayoutManager = new GridDefinitionLayoutManager(mViewGroup, null, adapterViewManager, attributes);
        mTestAdapter = new TestAdapter(VIEW_SIZE);
        adapterViewManager.setAdapter(mTestAdapter);
        doFirstLayout(VIEW_GROUP_WIDTH, VIEW_GROUP_HEIGHT);

        {
            final List<GridItemDefinition> gridItemDefinitions = new ArrayList<GridItemDefinition>();
            gridItemDefinitions.add(new GridItemDefinition(0, 0, 1, 1));
            gridItemDefinitions.add(new GridItemDefinition(0, 1, 1, 1));
            final GridGroupDefinition gridGroupDefinition = new GridGroupDefinition(true, gridItemDefinitions);
            listLayoutManager.addGridGroupDefinition(gridGroupDefinition);
        }
        {
            final List<GridItemDefinition> gridItemDefinitions = new ArrayList<GridItemDefinition>();
            gridItemDefinitions.add(new GridItemDefinition(0, 0, 1, 2));
            final GridGroupDefinition gridGroupDefinition = new GridGroupDefinition(true, gridItemDefinitions);
            listLayoutManager.addGridGroupDefinition(gridGroupDefinition);
        }
        {
            final List<GridItemDefinition> gridItemDefinitions = new ArrayList<GridItemDefinition>();
            gridItemDefinitions.add(new GridItemDefinition(0, 0, 1, 1));
            gridItemDefinitions.add(new GridItemDefinition(0, 1, 1, 1));
            gridItemDefinitions.add(new GridItemDefinition(1, 0, 1, 1));
            gridItemDefinitions.add(new GridItemDefinition(1, 1, 1, 1));
            final GridGroupDefinition gridGroupDefinition = new GridGroupDefinition(true, gridItemDefinitions);
            listLayoutManager.addGridGroupDefinition(gridGroupDefinition);
        }

    }

    @Test
    public void scrollItemOffScreen() {
        mTestAdapter.setAdapterSize(8);

        final Animation animation = new Animation();
        animation.newAnimation();
        doLayout(animation);

        assertThat(mViewGroup.mViews.size()).isEqualTo(2);

        View firstView = mViewGroup.mViews.get(0);
        assertThat(firstView.getTop()).isEqualTo(10);


        int displacement = -1300;
        animation.newAnimation();
        animation.setDisplacement(displacement);
        doLayout(animation);

        assertThat(mViewGroup.mViews.size()).isEqualTo(1);

        firstView = mViewGroup.mViews.get(0);

        assertThat(firstView.getTop()).isEqualTo(-10);
        assertThat(firstView.getBottom()).isEqualTo(90);

        displacement = -50;
        animation.newAnimation();
        animation.setDisplacement(displacement);
        doLayout(animation);

        firstView = mViewGroup.mViews.get(0);

        assertThat(firstView.getTop()).isEqualTo(-10);
        assertThat(firstView.getBottom()).isEqualTo(90);
    }

    private void doLayout() {
        doLayout(new Animation());
    }

    private void doLayout(Animation animation) {
        listLayoutManager.layout(mViewGroup, animation, false, 0, 0, VIEW_GROUP_WIDTH, VIEW_GROUP_HEIGHT);
    }

    private void doFirstLayout(int viewGroupWidth, int viewGroupHeight) {
        final int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(VIEW_GROUP_WIDTH, View.MeasureSpec.EXACTLY);
        final int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(VIEW_GROUP_HEIGHT, View.MeasureSpec.EXACTLY);
        mViewGroup.measure(widthMeasureSpec, heightMeasureSpec);
        mViewGroup.layout(0, 0, viewGroupWidth, viewGroupHeight);
    }

    public class MyViewGroup extends LinearLayout implements AdapterViewHandler {
        public final List<View> mViews = new ArrayList<View>();

        public MyViewGroup(Context context) {
            super(context);
        }

        public View forPosition(int position) {
            Collections.sort(mViews, new Comparator<View>() {
                @Override
                public int compare(View lhs, View rhs) {
                    return lhs.getLeft() - rhs.getLeft();
                }
            });

            return mViews.get(position);
        }

        @Override
        public boolean addViewInAdapterView(View view, int index, ViewGroup.LayoutParams layoutParams) {
            mViews.add(index, view);
            return true;
        }

        @Override
        public void removeViewInAdapterView(View view) {
            mViews.remove(view);
        }
    }

    public class TestAdapter extends BaseAdapter {
        private int mAdapterSize;
        private int mViewSize;

        public TestAdapter(int viewSize) {
            mViewSize = viewSize;
        }

        public void setAdapterSize(final int adapterSize) {
            mAdapterSize = adapterSize;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mAdapterSize;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FrameLayout outer = new FrameLayout(Robolectric.application);
            outer.setTag(position);
            outer.setLayoutParams(new ViewGroup.LayoutParams(mViewSize, mViewSize));

            // TODO: necessary to have an outer and an inner?
            final FrameLayout inner = new FrameLayout(Robolectric.application);
            inner.setLayoutParams(new ViewGroup.LayoutParams(mViewSize, mViewSize));
            outer.addView(inner);
            return outer;
        }
    }

}
