package mobi.parchment.widget.adapterview.utilities;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Emir Hasanbegovic on 2014-03-27.
 */
public class ViewGroupUtilities {

    public static int getViewGroupMeasuredHeight(final ViewGroup viewGroup){
        final int viewGroupHeightMeasureSpec = viewGroup.getMeasuredHeight();
        final int viewGroupHeightSize = View.MeasureSpec.getSize(viewGroupHeightMeasureSpec);
        return viewGroupHeightSize;
    }

    public static int getViewGroupMeasuredWidth(final ViewGroup viewGroup){
        final int viewGroupWidthMeasureSpec = viewGroup.getMeasuredWidth();
        final int viewGroupWidthSize = View.MeasureSpec.getSize(viewGroupWidthMeasureSpec);
        return viewGroupWidthSize;
    }

}
