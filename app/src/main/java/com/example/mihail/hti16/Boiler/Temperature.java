package com.example.mihail.hti16.Boiler;

import java.io.Serializable;

/**
 * Created by Юрий on 21.05.2015.
 */
public class Temperature implements Comparable<Temperature>, Serializable {
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
        if (Math.abs(this.round()-o.round()) <= 0.1) {
            this.value = o.value;
            return 0;
        }
        else
        {
            if(this.value> o.value)
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }

    }

    public void decrease() {
        value = value - 0.0005;
    }

    public void increase() {
        value = value + 0.001;
    }

    @Override
    public String toString() {
        String res = "";
        String in = Double.toString(this.value);
        int i =0;
        do  {
            res += in.charAt(i);
            i++;
        }while(in.charAt(i) != '.');
        res += in.charAt(i);
        res += in.charAt(i+1);
        return  res;
    }

    private double round() {
        int res =(int) ( value * 1000);
        res+=1;
        return (double)res/1000;
    }


}
