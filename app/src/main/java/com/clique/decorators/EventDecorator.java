package com.clique.decorators;


import java.util.Collection;
import java.util.HashSet;

import com.clique.materialcalendarview.CalendarDay;
import com.clique.materialcalendarview.DayViewDecorator;
import com.clique.materialcalendarview.DayViewFacade;
import com.clique.materialcalendarview.spans.DotSpan;


/**
 * Decorate several days with a dot
 */
public class EventDecorator implements DayViewDecorator {

    private int color;
    private HashSet<CalendarDay> dates;

    public EventDecorator(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));
    }
}
