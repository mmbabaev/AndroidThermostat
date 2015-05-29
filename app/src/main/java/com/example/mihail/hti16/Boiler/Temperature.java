package com.example.mihail.hti16.Boiler;

/**
 * Created by Юрий on 21.05.2015.
 */
public class Temperature implements Comparable<Temperature> {
    double value;

    public Temperature(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


    @Override
    public int compareTo(Temperature o) {
        return Double.compare(this.value,o.value);
    }

    public void decrease() {
        value = value - 0.05;
    }

    public void increase() {
        value = value + 0.1;
    }

    @Override
    public String toString() {
        try {
            return Double.toString(round()).substring(0, 4);
        } catch (Exception e) {
            return Double.toString(round());
        }
    }

    private double round() {
        int res =(int) ( value * 1000);
        res+=1;
        return (double)res/1000;
    }
}
