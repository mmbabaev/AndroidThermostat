package com.example.mihail.hti16.Boiler;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
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
        for (int i = 0; i < spans.size() ; i++) {
            if(spans.get(i).merge(span)) {
                return;
            }
        }
        if(spans.size()>=5) {
            return;
        }
        spans.add(span);
        sort();
    }

    public void sort() {
        Collections.sort(spans);
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

    public ArrayList<String> getGroup() {
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < spans.size(); i++) {
            res.add(spans.get(i).toString());
        }
        res.add("Add new interval...");
        return res;
    }

}