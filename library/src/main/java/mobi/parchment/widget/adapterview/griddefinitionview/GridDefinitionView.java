package mobi.parchment.widget.adapterview.griddefinitionview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Adapter;

import mobi.parchment.widget.adapterview.AdapterView;
import mobi.parchment.widget.adapterview.AdapterViewHandler;
import mobi.parchment.widget.adapterview.AdapterViewInitializer;
import mobi.parchment.widget.adapterview.AdapterViewManager;
import mobi.parchment.widget.adapterview.OnSelectedListener;
import mobi.parchment.widget.adapterview.SnapPosition;

import java.util.List;

/**
 * Created by Emir Hasanbegovic
 */
public class GridDefinitionView<ADAPTER extends Adapter> extends AdapterView<ADAPTER, DefinitionGroup> implements OnLongClickListener, OnClickListener, OnSelectedListener, AdapterViewHandler {

    private GridDefinitionLayoutManager mGridDefinitionLayoutManager;
    private boolean mIsVerticalScroll;


    public GridDefinitionView(Context context) {
        super(context);
    }

    public GridDefinitionView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GridDefinitionView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    @Override
    protected AdapterViewInitializer<DefinitionGroup> getAdapterViewInitializer(Context context, AttributeSet attributeSet) {
        final GridDefinitionAttributes gridDefinitionAttributes = new GridDefinitionAttributes(context, attributeSet);

        final boolean isViewPager = gridDefinitionAttributes.isViewPager();
        final int cellSpacing = (int) gridDefinitionAttributes.getCellSpacing();
        final boolean isCircularScroll = gridDefinitionAttributes.isIsCircularScroll();
        final boolean snapToPosition = gridDefinitionAttributes.isSnapToPosition();
        final int viewPagerInterval = gridDefinitionAttributes.getViewPagerInterval();
        final SnapPosition snapPosition = gridDefinitionAttributes.getSnapPosition();
        final boolean selectOnSnap = gridDefinitionAttributes.selectOnSnap();
        final boolean selectWhileScrolling = gridDefinitionAttributes.selectWhileScrolling();
        final float ratio = gridDefinitionAttributes.getRatio();
        mIsVerticalScroll = gridDefinitionAttributes.isVertical();

        final GridDefinitionLayoutManagerAttributes gridLayoutManagerAttributes = new GridDefinitionLayoutManagerAttributes(isCircularScroll, snapToPosition, isViewPager, viewPagerInterval, snapPosition, cellSpacing, selectOnSnap, selectWhileScrolling, mIsVerticalScroll, ratio);
        final AdapterViewManager adapterViewManager = new AdapterViewManager();
        mGridDefinitionLayoutManager = new GridDefinitionLayoutManager(this, this, adapterViewManager, gridLayoutManagerAttributes);

        final AdapterViewInitializer<DefinitionGroup> adapterViewAdapterInitializer = createAdapterViewInitializer(context, isViewPager, adapterViewManager, mGridDefinitionLayoutManager, mIsVerticalScroll);
        return adapterViewAdapterInitializer;
    }

    public void addGridGroupDefinition(final List<GridItemDefinition> gridItemDefinitions) {
        final GridGroupDefinition gridGroupDefinition = new GridGroupDefinition(mIsVerticalScroll, gridItemDefinitions);
        mGridDefinitionLayoutManager.addGridGroupDefinition(gridGroupDefinition);
    }

}
