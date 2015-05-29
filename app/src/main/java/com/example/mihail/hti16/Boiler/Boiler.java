package com.example.mihail.hti16.Boiler;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Юрий on 21.05.2015.
 */
public class Boiler {
    private Temperature currentTemperature;
    private Temperature targetTemperature;
    public boolean working;
    int mode;
    boolean temperatureChanging;
    Date nextChange;
    boolean temperatureOverriding;
    TimeTable timeTable;
    private final Temperature DAY_TEMPERATURE = new Temperature(30);
    private final Temperature NIGHT_TEMPERATURE = new Temperature(10);

    DayOfWeek curDay;
    Date curTime;
 //   private final Temperature OUTDOAR_TEMPERATURE = new Temperature(18);
    public Boiler(Temperature startTemperature, int boilerMode,TimeTable timeTable) {
        this.currentTemperature = startTemperature;
        this.targetTemperature = startTemperature;
        this.working = true;
        this.mode = boilerMode;
        this.timeTable = timeTable;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        day--;
        if(day == 0) {
            day = 7;
        }
        this.curDay = DayOfWeek.of(day);
        this.curTime = new Date(Calendar.getInstance().getTimeInMillis());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    work();
                } catch (InterruptedException e) {

                }
            }
        });
        thread.start();
    }

    public Temperature getCurrentTemperature() {
        return currentTemperature;
    }

    public void setTargetTemperature(Temperature target) {
        temperatureOverriding = true;
        this.targetTemperature = target;

    }

    public void stop() {
        this.working = false;
    }



    private void work() throws InterruptedException {
        while(this.working) {
            stopAndIncreaseTime();
            if(temperatureOverriding) {
                heating();
                if (nextChange == null) {
                    nextChange = timeTable.getNextChangeTime(curDay, curTime);
                }
                if(curTime.compareTo(nextChange) >=0 ) {
                    nextChange = null;
                    this.temperatureOverriding = false;
                }
            }
            else {
                //Работа по расписанию
                if(curTime.getHours()==12 ) {
                    int j = 5;
                }
                if(this.timeTable.getTemperatureMode(curDay,curTime)) {
                    this.targetTemperature = this.DAY_TEMPERATURE;
                }
                else {
                   this.targetTemperature = this.NIGHT_TEMPERATURE;
                }
                heating();
            }
        }
    }


    private void heating() {
        if (currentTemperature.compareTo(targetTemperature) == 0) {
            temperatureChanging = false;

            return;
        }
        if (currentTemperature.compareTo(targetTemperature) > 0) {
            currentTemperature.decrease();
            temperatureChanging = true;

            return;
        }
        if (currentTemperature.compareTo(targetTemperature) < 0) {
            currentTemperature.increase();
            temperatureChanging = true;

            return;
        }
    }
    private void stopAndIncreaseTime() throws InterruptedException {
        Thread.currentThread().sleep(mode);
        if(curTime.getHours() == 23 && curTime.getMinutes() == 59 && curTime.getSeconds() == 59)
        {
            curDay = DayOfWeek.of(((curDay.getValue()+1)<=7 ? curDay.getValue()+1:(curDay.getValue()+1)%7));
        }
        curTime.setTime(curTime.getTime()+1000);
    }

    public boolean isDayTemperature() {
        return this.timeTable.getTemperatureMode(curDay,curTime);
    }
}
