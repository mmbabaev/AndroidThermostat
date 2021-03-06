package com.example.mihail.hti16;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.mihail.hti16.Boiler.Boiler;
import com.example.mihail.hti16.Boiler.Storage;


public class SettingsActivity extends ActionBarActivity {

    final Boiler boiler = Storage.boiler;
    EditText dayEditText;
    EditText nightEditText;
    Switch timeTableSwitch;
    Switch vacationSwitch;

    SettingsActivity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Settings");

        dayEditText = (EditText)findViewById(R.id.dayEditText);
        nightEditText = (EditText)findViewById(R.id.nightEditText);
        timeTableSwitch = (Switch)findViewById(R.id.switch2);
        vacationSwitch = (Switch)findViewById(R.id.switch1);


        dayEditText.setText(Double.toString(boiler.getDayTemperature()));
        nightEditText.setText(Double.toString(boiler.getNightTemperature()));

        timeTableSwitch.setChecked(boiler.isUseTimeTable);
        vacationSwitch.setChecked(boiler.isOnVacation);

        Button saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double day;
                double night;
                try {
                    day = Double.parseDouble(dayEditText.getText().toString());
                    night = Double.parseDouble(nightEditText.getText().toString());
                } catch (Exception e) {
                    new AlertDialog.Builder(activity)
                            .setTitle("Error!")
                            .setMessage("Fields can't be empty!")

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                    return;
                }

                if (day < 5 || night < 5) {
                    new AlertDialog.Builder(activity)
                            .setTitle("Error!")
                            .setMessage("Temperature must be equal or higher than 5 °C")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }

                if (day >= 30 || night >= 30) {
                    new AlertDialog.Builder(activity)
                            .setTitle("Error!")
                            .setMessage("Temperature must be less than 30 °C")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return;
                }


                boiler.setDayTemperature(day);
                boiler.setNightTemperature(night);
                boiler.isUseTimeTable = timeTableSwitch.isChecked();
                boiler.isOnVacation = vacationSwitch.isChecked();



                finish();
            }
        });

        dayEditText.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("MyLog", "onStop()");

        Storage.saveBoiler();
    }
}
