package com.example.mihail.hti16.Boiler;


/**
 * Created by Юрий on 26.05.2015.
 */
public class TimeSpan {
    Time start;
    Time end;

    public TimeSpan(Time start, Time end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
       return start.toString()+ " - " + end.toString();
    }
}
