package com.parchment.widget.adapterview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.parchment.widget.adapterview.griddefinitionview.GridDefinitionLayoutManager;
import com.parchment.widget.adapterview.griddefinitionview.GridDefinitionLayoutManagerAttributes;
import com.parchment.widget.adapterview.griddefinitionview.GridGroupDefinition;
import com.parchment.widget.adapterview.griddefinitionview.GridItemDefinition;

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
public class GridDefinitionLayoutManagerTest {

    public static final int VIEW_GROUP_SIZE = 300;
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
        doFirstLayout(VIEW_GROUP_SIZE);

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

    }

    @Test
    public void initialMeasureTest() {
        mTestAdapter.setAdapterSize(3);
        doLayout();


        final View firstView = mViewGroup.mViews.get(0);
        final View secondView = mViewGroup.mViews.get(1);
        final View thirdView = mViewGroup.mViews.get(2);

        assertThat(firstView.getWidth()).isLessThanOrEqualTo(135);
        assertThat(secondView.getWidth()).isLessThanOrEqualTo(135);
        assertThat(thirdView.getWidth()).isLessThanOrEqualTo(280);

        assertThat(firstView.getHeight()).isLessThanOrEqualTo(135);
        assertThat(secondView.getHeight()).isLessThanOrEqualTo(135);
        assertThat(thirdView.getHeight()).isLessThanOrEqualTo(135);

    }

    @Test
    public void initialLayoutTest() {
        mTestAdapter.setAdapterSize(3);
        final Animation animation = new Animation();
        animation.newAnimation();
        doLayout(animation);

        View firstView = mViewGroup.mViews.get(0);
        View secondView = mViewGroup.mViews.get(1);
        View thirdView = mViewGroup.mViews.get(2);

        assertThat(firstView.getTop()).isEqualTo(10);
        assertThat(secondView.getTop()).isEqualTo(10);
        assertThat(thirdView.getTop()).isEqualTo(155);

        assertThat(firstView.getLeft()).isEqualTo(10);
        assertThat(secondView.getLeft()).isEqualTo(155);
        assertThat(thirdView.getLeft()).isEqualTo(10);

    }


    @Test
    public void initialScrollTest() {
        mTestAdapter.setAdapterSize(3);

        final Animation animation = new Animation();
        animation.newAnimation();
        doLayout(animation);


        int displacement = -10;
        animation.newAnimation();
        animation.setDisplacement(displacement);
        doLayout(animation);

        View firstView = mViewGroup.mViews.get(0);
        View secondView = mViewGroup.mViews.get(1);
        View thirdView = mViewGroup.mViews.get(2);

        assertThat(firstView.getTop()).isEqualTo(10);
        assertThat(secondView.getTop()).isEqualTo(10);
        assertThat(thirdView.getTop()).isEqualTo(155);
    }

    @Test
    public void layoutNonFullSectionsWithRepetition() {
        mTestAdapter.setAdapterSize(1);

        final Animation animation = new Animation();
        animation.newAnimation();
        doLayout(animation);

        int displacement = -150;
        animation.newAnimation();
        animation.setDisplacement(displacement);
        doLayout(animation);

        animation.newAnimation();
        animation.setDisplacement(-displacement);
        doLayout(animation);
    }

    @Test
    public void scrollOneItemOffOfTop() {
        mTestAdapter.setAdapterSize(1);

        final Animation animation = new Animation();
        animation.newAnimation();
        doLayout(animation);

        int displacement = -10;
        animation.newAnimation();
        animation.setDisplacement(displacement);
        doLayout(animation);

        View firstView = mViewGroup.mViews.get(0);

        assertThat(firstView.getTop()).isEqualTo(10);

    }

    @Test
    public void scrollOneItemOffOfBottom() {
        mTestAdapter.setAdapterSize(1);

        final Animation animation = new Animation();
        animation.newAnimation();
        doLayout(animation);

        int displacement = 320;
        animation.newAnimation();
        animation.setDisplacement(displacement);
        doLayout(animation);

        View firstView = mViewGroup.mViews.get(0);

        assertThat(firstView.getTop()).isEqualTo(155);
    }

    @Test
    public void scrollTwoItemsOffOfTop() {
        mTestAdapter.setAdapterSize(2);

        final Animation animation = new Animation();
        animation.newAnimation();
        doLayout(animation);

        View firstView = mViewGroup.mViews.get(0);

        assertThat(firstView.getTop()).isEqualTo(10);

        int displacement = -10;
        animation.newAnimation();
        animation.setDisplacement(displacement);
        doLayout(animation);

        firstView = mViewGroup.mViews.get(0);

        assertThat(firstView.getTop()).isEqualTo(10);

    }

    @Test
    public void scrollTwoItemsOffOfBottom() {
        mTestAdapter.setAdapterSize(2);

        final Animation animation = new Animation();
        animation.newAnimation();
        doLayout(animation);

        int displacement = 320;
        animation.newAnimation();
        animation.setDisplacement(displacement);
        doLayout(animation);

        View firstView = mViewGroup.mViews.get(0);

        assertThat(firstView.getTop()).isEqualTo(155);


    }

    @Test
    public void gridRepetitionTest() {
        mTestAdapter.setAdapterSize(8);

        final Animation animation = new Animation();
        animation.newAnimation();
        doLayout(animation);

        View firstView = mViewGroup.mViews.get(3);
        View secondView = mViewGroup.mViews.get(4);

        assertThat(firstView.getTop()).isEqualTo(300);
        assertThat(secondView.getTop()).isEqualTo(300);

        assertThat(firstView.getLeft()).isEqualTo(10);
        assertThat(secondView.getLeft()).isEqualTo(155);
    }

    @Test
    public void scrollItemOffScreen() {
        mTestAdapter.setAdapterSize(8);

        final Animation animation = new Animation();
        animation.newAnimation();
        doLayout(animation);

        int displacement = -160;
        animation.newAnimation();
        animation.setDisplacement(displacement);
        doLayout(animation);

        View firstView = mViewGroup.mViews.get(0);
        View secondView = mViewGroup.mViews.get(1);

        assertThat(firstView.getLeft()).isEqualTo(10);
        assertThat(firstView.getRight()).isEqualTo(290);
        assertThat(firstView.getTop()).isEqualTo(-5);
        assertThat(firstView.getBottom()).isEqualTo(130);
        assertThat(secondView.getLeft()).isEqualTo(10);
        assertThat(secondView.getRight()).isEqualTo(145);
        assertThat(secondView.getTop()).isEqualTo(140);
        assertThat(secondView.getBottom()).isEqualTo(275);

        displacement = -displacement;
        animation.newAnimation();
        animation.setDisplacement(displacement);
        doLayout(animation);

        assertThat(mViewGroup.mViews.size()).isEqualTo(5);

        firstView = mViewGroup.mViews.get(0);
        secondView = mViewGroup.mViews.get(1);
        View thirdView = mViewGroup.mViews.get(2);
        assertThat(firstView.getLeft()).isEqualTo(10);
        assertThat(firstView.getRight()).isEqualTo(145);
        assertThat(firstView.getTop()).isEqualTo(10);
        assertThat(firstView.getBottom()).isEqualTo(145);

        assertThat(secondView.getLeft()).isEqualTo(155);
        assertThat(secondView.getRight()).isEqualTo(290);
        assertThat(secondView.getTop()).isEqualTo(10);
        assertThat(secondView.getBottom()).isEqualTo(145);

        assertThat(thirdView.getLeft()).isEqualTo(10);
        assertThat(thirdView.getRight()).isEqualTo(290);
        assertThat(thirdView.getTop()).isEqualTo(155);
        assertThat(thirdView.getBottom()).isEqualTo(290);
    }

    private void doLayout() {
        doLayout(new Animation());
    }

    private void doLayout(Animation animation) {
        listLayoutManager.layout(mViewGroup, animation, false, 0, 0, VIEW_GROUP_SIZE, VIEW_GROUP_SIZE);
    }

    private void doFirstLayout(int viewGroupSize) {
        final int measureSpec = View.MeasureSpec.makeMeasureSpec(VIEW_GROUP_SIZE, View.MeasureSpec.EXACTLY);
        mViewGroup.measure(measureSpec, measureSpec);
        mViewGroup.layout(0, 0, viewGroupSize, viewGroupSize);
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
