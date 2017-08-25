package com.clique.decorators;

import android.graphics.drawable.Drawable;

import java.util.Date;

import com.clique.materialcalendarview.CalendarDay;
import com.clique.materialcalendarview.DayViewDecorator;
import com.clique.materialcalendarview.DayViewFacade;


/**
 * Decorate a day by making the text big and bold
 */
public class OneDayDecorator implements DayViewDecorator {

    private CalendarDay date;
    private Drawable drawable;

    public OneDayDecorator(Drawable drawable) {
        date = CalendarDay.today();
        this.drawable = drawable;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
    }

    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
