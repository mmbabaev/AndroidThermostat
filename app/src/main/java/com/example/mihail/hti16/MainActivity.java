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
import com.example.mihail.hti16.Boiler.Storage;
import com.example.mihail.hti16.Boiler.Temperature;
import com.example.mihail.hti16.Boiler.Time;


public class MainActivity extends ActionBarActivity {

    Button setButton;
    DataPicker dpLeft;
    DataPicker dpRight;

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

        Storage.PACKAGE_NAME = getPackageName();
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


        dpLeft = (DataPicker)findViewById(R.id.dpLeft);
        dpRight = (DataPicker)findViewById(R.id.dpRight);

        String [] leftValues = new String[25];
        for (int i = 5; i < 30; i++) {

            leftValues[i - 5] = Integer.toString(i) + " ";
        }

        String [] rightValues = new String[10];
        for (int i = 0; i < 10; i++) {
            rightValues[i] = "" + Integer.toString(i);
        }


        dpLeft.setValues(leftValues);
        dpRight.setValues(rightValues);


        setButton = (Button)findViewById(R.id.setButton);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double temperatureValue = Double.parseDouble(dpLeft.getValue());
                temperatureValue += 0.1 * Double.parseDouble(dpRight.getValue());

                boiler.setTargetTemperature(new Temperature(temperatureValue));
                topImage.setVisibility(View.INVISIBLE);
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

    public Time correctTime(int hour,int minute,int seconds) {
        return new Time(hour, minute, seconds);
    }

    public void listenTemp(final Boiler boiler) throws InterruptedException {
        while (boiler.working) {
            Thread.sleep(1000);
            System.out.println("Current temperature  " + boiler.getCurrentTemperature() + "  current Time " +  boiler.curTime.toString() + "   current day   " + boiler.curDay + " current mode: " + boiler.isDayTemperature());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (boiler.isOnVacation) {
                        setButton.setEnabled(false);
                        dpLeft.setEnabled(false);
                        dpRight.setEnabled(false);
                        calendarButton.setEnabled(false);
                        topImage.setVisibility(View.VISIBLE);
                        int id = getResources().getIdentifier("vacation", "drawable", getPackageName());
                        topImage.setImageResource(id);
                        return;
                    }
                    setButton.setEnabled(true);
                    dpLeft.setEnabled(true);
                    dpRight.setEnabled(true);
                    calendarButton.setEnabled(true);

                    if (boiler.temperatureOverriding == false) {
                        topImage.setVisibility(View.VISIBLE);
                        if (boiler.isDayTemperature()) {
                            int id = getResources().getIdentifier("sun", "drawable", getPackageName());
                            topImage.setImageResource(id);
                        } else {
                            int id = getResources().getIdentifier("semimoon", "drawable", getPackageName());
                            topImage.setImageResource(id);
                        }
                    }
                    else {
                        topImage.setVisibility(View.INVISIBLE);
                    }

                    currentTemperatureTextView.setText(boiler.getCurrentTemperature().toString() + " °C");
                }
            });
        }
    }

    public void showDayChooseActivity() {
        Intent intent = new Intent(this, TimeTableShowerActivity.class);

        startActivity(intent);
    }

    public void showSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);

        startActivity(intent);
    }
}
