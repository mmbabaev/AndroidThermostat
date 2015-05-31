package com.example.mihail.hti16.Boiler;

import android.util.Log;

import com.example.mihail.hti16.ExpListAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.acl.LastOwnerException;
import java.util.List;


public class Storage {
    public static List<DayOfWeek> chosenDays;
    public static Boiler boiler  = new Boiler(new Temperature(18.5), BoilerMode.TEST_MODE);
    public static String PACKAGE_NAME;
    public static String PATH;

    public static ExpListAdapter adapter;

    public static Boiler getBoiler() {
        try {
            FileInputStream fis = new FileInputStream(PATH + "/boiler.out");
            ObjectInputStream oin = new ObjectInputStream(fis);
            boiler = (Boiler) oin.readObject();
            Log.d("getBoiler",  "COMPLETE");
            return boiler;
        }
        catch (Exception e) {
            Log.d("getBoiler",  "ERROR" + e.getLocalizedMessage());
            return boiler;
        }
    }

    public static void saveBoiler() {
        try {
            File file = new File(PATH + "/boiler.out");
            if(!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(Storage.boiler);
            oos.flush();
            oos.close();
            Log.d("saveBoiler",  "COMPLETE");
        }
        catch (Exception ex) {
            Log.d("saveBoiler",  "ERROR" + ex.getLocalizedMessage());
        }
    }
}
