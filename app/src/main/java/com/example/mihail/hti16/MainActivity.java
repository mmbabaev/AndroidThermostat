package com.example.mihail.hti16;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mihail.hti16.Boiler.Boiler;
import com.example.mihail.hti16.Boiler.DayOfWeek;
import com.example.mihail.hti16.Boiler.Storage;
import com.example.mihail.hti16.Boiler.Temperature;
import com.example.mihail.hti16.Boiler.TimeTable;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends ActionBarActivity {

    TextView currentTemperatureTextView;
    ImageView topImage;
    ImageButton calendarButton;
    ImageButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentTemperatureTextView = (TextView)findViewById(R.id.textView);
        topImage = (ImageView)findViewById(R.id.topImage);


        TimeTable timeTable = new TimeTable();

        timeTable.addSpan(DayOfWeek.FRIDAY, correctTime(19, 0, 0), correctTime(19, 30, 30));
        timeTable.addSpan(DayOfWeek.FRIDAY, correctTime(20, 0, 0), correctTime(20, 30, 30));

        final Boiler boiler = Storage.boiler;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listenTemp(boiler);
                } catch (InterruptedException e) {

                }
            }
        });
        thread.start();



        final DataPicker dpLeft = (DataPicker)findViewById(R.id.dpLeft);
        final DataPicker dpRight = (DataPicker)findViewById(R.id.dpRight);
        String [] leftValues = new String[26];
        for (int i = 5; i < 31; i++) {

            leftValues[i - 5] = Integer.toString(i) + " ";
        }

        String [] rightValues = new String[10];
        for (int i = 0; i < 10; i++) {
            rightValues[i] = "" + Integer.toString(i);
        }


        dpLeft.setValues(leftValues);
        dpRight.setValues(rightValues);


        Button setButton = (Button)findViewById(R.id.setButton);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double temperatureValue = Double.parseDouble(dpLeft.getValue());
                temperatureValue += 0.1 * Double.parseDouble(dpRight.getValue());

                boiler.setTargetTemperature(new Temperature(temperatureValue));
            }
        });

        calendarButton = (ImageButton)findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDayChooseActivity();
            }
        });

        settingsButton = (ImageButton)findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingsActivity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Date correctTime(int hour,int minute,int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    public void listenTemp(final Boiler boiler) throws InterruptedException {
        while (boiler.working) {
            Thread.sleep(1000);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (boiler.isDayTemperature()) {
                        int id = getResources().getIdentifier("sun", "drawable", getPackageName());
                        topImage.setImageResource(id);
                    }
                    else {
                        int id = getResources().getIdentifier("semimoon", "drawable", getPackageName());
                        topImage.setImageResource(id);
                    }

                    currentTemperatureTextView.setText(boiler.getCurrentTemperature().toString() + " °C");
                }
            });
        }
    }

    public void showDayChooseActivity() {
        Intent intent = new Intent(this, DayChoseActivity.class);

        startActivity(intent);
    }

    public void showSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);

        startActivity(intent);
    }
}
