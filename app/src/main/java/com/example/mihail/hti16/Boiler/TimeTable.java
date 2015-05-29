package com.example.mihail.hti16.Boiler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Юрий on 26.05.2015.
 */
public class TimeTable {
    List<Day> week = new ArrayList<Day>();

    boolean changed = false;
    public TimeTable() {
        for (int i = 0; i < 7; i++) {
            week.add(new Day(DayOfWeek.of(i+1)));
        }
    }

    public void addSpan(DayOfWeek day, TimeSpan span) {
        week.get(day.getValue()-1).addSpan(span);
        changed = true;
    }

    public void addSpan(DayOfWeek day, Date start,Date end ) {
        week.get(day.getValue()-1).addSpan(new TimeSpan(start,end));
        changed = true;
    }



    /**
     Если вернул true день
     false - ночь
     */
    public boolean getTemperatureMode(DayOfWeek day,Date curTime) {
        return week.get(day.getValue()-1).getTemperatureMode(curTime);
    }

    public Date getNextChangeTime(DayOfWeek day,Date curTime) {
       return week.get(day.getValue()-1).getNextChangeTime(curTime);
    }
}
