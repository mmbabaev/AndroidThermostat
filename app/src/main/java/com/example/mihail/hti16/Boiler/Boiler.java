package com.example.mihail.hti16.Boiler;


import java.io.Serializable;
import java.util.Calendar;


/**
 * Created by Юрий on 21.05.2015.
 */
public class Boiler implements Serializable {

    private Temperature currentTemperature;
    private Temperature targetTemperature;
    public boolean working;
    int mode;
    boolean temperatureChanging;
    Time nextChange;
    public boolean temperatureOverriding;
    public TimeTable timeTable;
    private Temperature DAY_TEMPERATURE = new Temperature(20);
    private Temperature NIGHT_TEMPERATURE = new Temperature(10);
    Thread thread;
    public boolean isOnVacation = false;
    public boolean isUseTimeTable =true;

 public    DayOfWeek curDay;
  public   Time curTime;

    //   private final Temperature OUTDOAR_TEMPERATURE = new Temperature(18);
    public Boiler(Temperature startTemperature, int boilerMode) {
        this.currentTemperature = startTemperature;
        this.targetTemperature = startTemperature;
        this.working = true;
        this.mode = boilerMode;
        TimeTable table = new TimeTable();

        table.addSpan(DayOfWeek.SUNDAY, correctTime(14, 0, 0), correctTime(14, 30, 30));
        table.addSpan(DayOfWeek.SUNDAY, correctTime(17, 40, 0), correctTime(18, 00, 30));

        this.timeTable = table;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        day--;
        if (day == 0) {
            day = 7;
        }
        this.curDay = DayOfWeek.of(day);

        this.curTime = new Time(Calendar.getInstance().getTime());
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

    public Boiler(Temperature startTemperature, int boilerMode, TimeTable timeTable) {
        this.currentTemperature = startTemperature;
        this.targetTemperature = startTemperature;
        this.working = true;
        this.mode = boilerMode;

        this.timeTable = timeTable;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        day--;
        if (day == 0) {
            day = 7;
        }
        this.curDay = DayOfWeek.of(day);
        this.curTime = new Time(Calendar.getInstance().getTime());
        this.thread = new Thread(new Runnable() {
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
        while (this.working) {
            stopAndIncreaseTime();
            if (isOnVacation) {
                this.targetTemperature = this.NIGHT_TEMPERATURE;
                continue;
            }

            if (temperatureOverriding) {
                heating();
                if (nextChange == null) {
                    nextChange = timeTable.getNextChangeTime(curDay, curTime);
                }
                if (curTime.compareTo(nextChange) >= 0) {
                    nextChange = null;
                    if (isUseTimeTable) {
                        this.temperatureOverriding = false;
                    }
                }
            } else {
                //Работа по расписанию
                if (this.timeTable.getTemperatureMode(curDay, curTime)) {
                    this.targetTemperature = this.DAY_TEMPERATURE;
                } else {
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
        if (curTime.getHours() == 23 && curTime.getMinutes() == 59 && curTime.getSeconds() == 59) {
            curDay = DayOfWeek.of(((curDay.getValue() + 1) <= 7 ? curDay.getValue() + 1 : (curDay.getValue() + 1) % 7));
        }
        curTime.increment();
    }

    public boolean isDayTemperature() {
        return this.timeTable.getTemperatureMode(curDay, curTime);
    }

    public Time correctTime(int hour, int minute, int seconds) {
        return new Time(hour, minute, seconds);
    }

    public void setDayTemperature(double value) {
        DAY_TEMPERATURE = new Temperature(value);
    }

    public void setNightTemperature(double value) {
        NIGHT_TEMPERATURE = new Temperature(value);
    }

    public double getDayTemperature() {
        return DAY_TEMPERATURE.getValue();
    }

    public double getNightTemperature() {
        return NIGHT_TEMPERATURE.getValue();
    }
}