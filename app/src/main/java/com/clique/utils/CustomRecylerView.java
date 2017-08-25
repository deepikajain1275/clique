package com.clique.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Deepika on 12-02-2017.
 */

public class CustomRecylerView extends RecyclerView {
    public CustomRecylerView(Context context) {
        super(context);
    }

    public CustomRecylerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecylerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        View view = getLayoutManager().getFocusedChild();
        if (null == view) {
            return super.getChildDrawingOrder(childCount, i);
        }
        int position = indexOfChild(view);

        if (position < 0) {
            return super.getChildDrawingOrder(childCount, i);
        }
        if (i == childCount - 1) {
            return position;
        }
        if (i == position) {
            return childCount - 1;
        }
        return super.getChildDrawingOrder(childCount, i);
    }

}
