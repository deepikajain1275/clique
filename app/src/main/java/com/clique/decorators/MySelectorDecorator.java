package com.clique.decorators;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.clique.R;
import com.clique.materialcalendarview.CalendarDay;
import com.clique.materialcalendarview.DayViewDecorator;
import com.clique.materialcalendarview.DayViewFacade;


/**
 * Use a custom selector
 */
public class MySelectorDecorator implements DayViewDecorator {

    private final Drawable drawable;

    public MySelectorDecorator(Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.my_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
