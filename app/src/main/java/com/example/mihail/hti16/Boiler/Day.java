package com.example.mihail.hti16.Boiler;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Юрий on 26.05.2015.
 *
 * Храним только дневные промежутки
 */
public class Day {
    private List<TimeSpan> spans = new ArrayList<TimeSpan>();
    DayOfWeek dayOfWeek;

    public Day(DayOfWeek day) {
        this.dayOfWeek = day;
    }

    public void addSpan(TimeSpan span) {
        if(spans.size()>=5) {
            return;
        }
        spans.add(span);
    }

    public void addSpan(Time start, Time end) {
        if(spans.size()>=5) {
            return;
        }
        spans.add(new TimeSpan(start,end));
    }

    public void clear() {
        spans.clear();
    }



    public Time getNextChangeTime(Time curTime) {
        TimeSpan curSpan;
        for (int i = 0; i < spans.size(); i++) {
            curSpan = spans.get(i);
            if(curTime.compareTo(curSpan.start)<0)
            {
                return curSpan.start;
            }
            if(curTime.compareTo(curSpan.end)<0) {
                return curSpan.end;
            }
        }
        return new Time(23,59,59);
    }
    /**
     Если вернул true день
     false - ночь
     */
    public boolean getTemperatureMode(Time curTime) {
        TimeSpan curSpan;
        for (int i = 0; i < spans.size(); i++) {
            curSpan = spans.get(i);
            if(curTime.compareTo(curSpan.end) > 0) {
                continue;
            }
            if(curTime.compareTo(curSpan.start) >= 0) {
                return true;
            }
        }
        return  false;
    }



}