package com.example.mihail.hti16;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;

import com.example.mihail.hti16.Boiler.DayOfWeek;
import com.example.mihail.hti16.Boiler.Storage;

import java.util.ArrayList;
import java.util.List;


public class DayChoseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_day_chose);

        final Switch switcher2 = (Switch)findViewById(R.id.switch4);
        switcher2.setChecked(true);
        switcher2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!switcher2.isChecked()) {
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_day_chose, menu);
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

    public void onSetButton(View view) {
        ArrayList<DayOfWeek> result = new ArrayList<>();
        if (((CheckBox) findViewById(R.id.checkBox)).isChecked()) {
            result.add(DayOfWeek.MONDAY);
        }
        if (((CheckBox) findViewById(R.id.checkBox2)).isChecked()) {
            result.add(DayOfWeek.TUESDAY);
        }
        if (((CheckBox) findViewById(R.id.checkBox3)).isChecked()) {
            result.add(DayOfWeek.WEDNESDAY);
        }
        if (((CheckBox) findViewById(R.id.checkBox4)).isChecked()) {
            result.add(DayOfWeek.THURSDAY);
        }
        if (((CheckBox) findViewById(R.id.checkBox5)).isChecked()) {
            result.add(DayOfWeek.FRIDAY);
        }
        if (((CheckBox) findViewById(R.id.checkBox6)).isChecked()) {
            result.add(DayOfWeek.SATURDAY);
        }
        if (((CheckBox) findViewById(R.id.checkBox7)).isChecked()) {
            result.add(DayOfWeek.SUNDAY);
        }
        Storage.chosenDays = result;

        TimeTableShowerActivity.showAddTimeAlert(result);
    }


}




