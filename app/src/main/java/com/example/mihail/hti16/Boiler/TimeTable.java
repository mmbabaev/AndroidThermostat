package com.example.mihail.hti16.Boiler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Юрий on 26.05.2015.
 */
public class TimeTable implements Serializable {
    List<Day> week = new ArrayList<Day>();

    public boolean changed = false;
    public TimeTable() {
        for (int i = 0; i < 7; i++) {
            week.add(new Day(DayOfWeek.of(i+1)));
        }
    }

    public void addSpan(DayOfWeek day, TimeSpan span) {
        week.get(day.getValue()-1).addSpan(span);
        changed = true;
    }

    public void addSpan(DayOfWeek day, Time start,Time end ) {
        week.get(day.getValue()-1).addSpan(new TimeSpan(start,end));
        changed = true;
    }

    public void remove(int day, int inedx) {
        week.get(day).remove(inedx);
    }

    /**
     Если вернул true день
     false - ночь
     */
    public boolean getTemperatureMode(DayOfWeek day,Time curTime) {
        return week.get(day.getValue()-1).getTemperatureMode(curTime);
    }

    public Time getNextChangeTime(DayOfWeek day,Time curTime) {
       return week.get(day.getValue()-1).getNextChangeTime(curTime);
    }

    public ArrayList<ArrayList<String>> getGroups() {
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < week.size();i++){
            res.add(week.get(i).getGroup());
        }
        return res;
    }
}
