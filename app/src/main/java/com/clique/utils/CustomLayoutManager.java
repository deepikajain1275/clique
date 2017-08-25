package com.clique.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class CustomLayoutManager extends LinearLayoutManager {
    private int mParentWidth;
    private int mItemWidth;

    public CustomLayoutManager(Context context, int parentWidth, int itemWidth) {
        super(context);
        mParentWidth = parentWidth;
        mItemWidth = itemWidth;
    }

    public CustomLayoutManager(Context context, int orientation, boolean reverseLayout, int mParentWidth, int mItemWidth) {
        super(context, orientation, reverseLayout);
        this.mParentWidth = mParentWidth;
        this.mItemWidth = mItemWidth;
    }

    @Override
    public int getPaddingLeft() {
        return Math.round(mParentWidth / 2f - mItemWidth / 2f);
    }

    @Override
    public int getPaddingRight() {
        return getPaddingLeft();
    }
}