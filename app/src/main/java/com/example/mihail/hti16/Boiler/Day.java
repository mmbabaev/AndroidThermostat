package com.example.mihail.hti16.Boiler;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
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

    public void addSpan(Date start, Date end) {
        if(spans.size()>=5) {
            return;
        }
        spans.add(new TimeSpan(start,end));
    }

    public void clear() {
        spans.clear();
    }



    public Date getNextChangeTime(Date curTime) {
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
        return new Date(23,59,59);
    }
    /**
     Если вернул true день
     false - ночь
     */
    public boolean getTemperatureMode(Date curTime) {
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

    private int compareIgnoreYear(Date a,Date b){
       if(a.getHours()>b.getHours()) {
           return 1;
       }
       if(a.getHours()<b.getHours()) {
           return -1;
       }
       if(a.getHours()==b.getHours()) {
           if(a.getMinutes()>b.getMinutes()) {
               return 1;
           }
           if(a.getMinutes()<b.getMinutes()) {
               return -1;
           }

           if(a.getMinutes() == b.getMinutes()) {
               if(a.getSeconds()>b.getSeconds()) {
                   return 1;
               }
               if(a.getSeconds()<b.getSeconds()) {
                   return -1;
               }

               if(a.getSeconds() == b.getSeconds()) {
                   return 0;
               }
           }
       }
       return  0;
     }
}
