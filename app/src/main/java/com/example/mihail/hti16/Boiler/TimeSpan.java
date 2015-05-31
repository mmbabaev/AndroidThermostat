package com.example.mihail.hti16.Boiler;


import java.io.Serializable;

/**
 * Created by Юрий on 26.05.2015.
 */
public class TimeSpan implements Comparable, Serializable {
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

    public boolean merge(TimeSpan other) {
        if(this.start.compareTo(other.start)<=0 && this.end.compareTo(other.start) > 0 && other.end.compareTo(this.end) > 0) {
            this.end = other.end;
            return true;
        }

        if(this.start.compareTo(other.start)>=0 && this.end.compareTo(other.start) > 0 && other.end.compareTo(this.end) < 0&& other.end.compareTo(this.start)>=0) {
            this.start = other.start;
            return  true;
        }

        if(this.start.compareTo(other.start)<=0 && this.end.compareTo(other.start) >= 0 && other.end.compareTo(this.end) <= 0) {
            return true;
        }

        if(this.start.compareTo(other.start)>=0 && this.end.compareTo(other.end) <= 0) {
            this.start = other.start;
            this.end = other.end;
            return true;
        }

        return false;
    }

    @Override
    public int compareTo(Object o) {
        return this.start.compareTo(((TimeSpan)o).start);
    }
}
