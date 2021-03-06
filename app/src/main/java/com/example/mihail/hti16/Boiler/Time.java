package com.example.mihail.hti16.Boiler;

import java.io.Serializable;
import java.util.Date;
/**
 * Created by on 30.05.2015.
 */
public class Time implements Serializable {
    int hour;
    int minute;
    int seconds;

    public int getHours() {
        return hour;
    }

    public void setHour(int hour) {
        if(hour == 24) {
            this.hour= 0;
            return;
        }
        this.hour = hour;
    }

    public int getMinutes() {
        return minute;
    }

    public void setMinute(int minute) {
        if(minute == 60 ) {
            setHour(hour +1);
            minute = 0;
        }
        this.minute = minute;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        if(seconds ==60) {
            setMinute(minute+1);
            seconds=0;
        }
        this.seconds = seconds;
    }

    public Time(int hour,int minute,int seconds) {
        this.hour = hour;
        this.minute = minute;
        this.seconds = seconds;
    }

    public Time(Date date) {
        this.hour=date.getHours();
        this.minute= date.getMinutes();
        this.seconds= date.getSeconds();
    }

    public void increment() {
        this.setSeconds(seconds+1);
    }




    @Override
    public String toString() {
        String h = Integer.toString(hour);

        if(h.length()==1) {
            h = '0'+ h;
        }

        String m = Integer.toString(minute);

        if(m.length() == 1) {
            m = '0' + m;
        }

        String s = Integer.toString(seconds);

        if(s.length()==1) {
            s = '0' + s;
        }

        return h+ ":" + m+ ":"+s;
    }

    public int compareTo(Time b) {
        if(this.getHours()>b.getHours()) {
            return 1;
        }
        if(this.getHours()<b.getHours()) {
            return -1;
        }
        if(this.getHours()==b.getHours()) {
            if(this.getMinutes()>b.getMinutes()) {
                return 1;
            }
            if(this.getMinutes()<b.getMinutes()) {
                return -1;
            }

            if(this.getMinutes() == b.getMinutes()) {
                if(this.getSeconds()>b.getSeconds()) {
                    return 1;
                }
                if(this.getSeconds()<b.getSeconds()) {
                    return -1;
                }

                if(this.getSeconds() == b.getSeconds()) {
                    return 0;
                }
            }
        }
        return  0;
    }

}
