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
        return Double.toString(value).substring(0,4);
    }
}
