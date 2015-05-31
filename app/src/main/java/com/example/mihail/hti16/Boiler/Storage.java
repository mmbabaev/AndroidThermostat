package com.example.mihail.hti16.Boiler;

import java.util.List;


public class Storage {
    public static List<DayOfWeek> chosenDays;
    public static Boiler boiler  = new Boiler(new Temperature(18.5), BoilerMode.TEST_MODE);
    public static String PACKAGE_NAME;
}
