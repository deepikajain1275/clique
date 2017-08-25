package com.clique.gallery;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 实现ViewPager左右滑动时的时差
 * Created by xmuSistone on 2016/9/18.
 */
public class CustPagerTransformer implements ViewPager.PageTransformer {

    private int maxTranslateOffsetX;
    private ViewPager viewPager;
    private int type = 0;

    public CustPagerTransformer(Context context, int type) {
        this.maxTranslateOffsetX = dp2px(context, 180);
        this.type = type;
    }

    public void transformPage(View view, float position) {
        if (viewPager == null) {
            viewPager = (ViewPager) view.getParent();
        }

        int leftInScreen = view.getLeft() - viewPager.getScrollX();
        int centerXInViewPager = leftInScreen + view.getMeasuredWidth() / 2;
        int offsetX = centerXInViewPager - viewPager.getMeasuredWidth() / 2;
        float offsetRate = (float) offsetX * 0.38f / viewPager.getMeasuredWidth();
        float scaleFactor = 1 - Math.abs(offsetRate);
        if (scaleFactor > 0) {
            if (scaleFactor > .88) {
                view.setAlpha(1f);
            } else {
                view.setAlpha(0.5f);
            }
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setTranslationX(-maxTranslateOffsetX * offsetRate);
        }
    }

    private int dp2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    public void setType(int type) {
        this.type = type;
        viewPager.setCurrentItem(viewPager.getCurrentItem());
    }
}
