package com.parchment.widget.adapterview.griddefinitionview;

/**
 * Created by Emir Hasanbegovic on 2014-03-03.
 */
public class GridItemDefinition {

    private final int mTop;
    private final int mLeft;
    private final int mHeight;
    private final int mWidth;


    public GridItemDefinition(final int top, final int left, final int height, final int width) {
        mTop = top;
        mLeft = left;
        mHeight = height;
        mWidth = width;
    }

    public int getTop() {
        return mTop;
    }

    public int getLeft() {
        return mLeft;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getWidth() {
        return mWidth;
    }
}
