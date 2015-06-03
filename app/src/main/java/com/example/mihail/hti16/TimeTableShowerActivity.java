package com.example.mihail.hti16;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mihail.hti16.Boiler.Boiler;
import com.example.mihail.hti16.Boiler.DayOfWeek;
import com.example.mihail.hti16.Boiler.Storage;
import com.example.mihail.hti16.Boiler.Time;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class TimeTableShowerActivity extends ActionBarActivity {

    static ActionBarActivity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_shower);


        act = this;


        setTitle("Timetable");
        ExpandableListView listView = (ExpandableListView)findViewById(R.id.lvExp);
        ArrayList<ArrayList<String>> groups = Storage.boiler.timeTable.getGroups();
        Storage.adapter = new ExpListAdapter(getApplicationContext(), groups);
        listView.setAdapter(Storage.adapter);
        
        final Switch switcher = (Switch)findViewById(R.id.switch3);
       switcher.setChecked(false);

        switcher.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switcher.isChecked()) {
                    Intent intent = new Intent(TimeTableShowerActivity.this, DayChoseActivity.class);

                    startActivity(intent);
                }
            }
        }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_table_shower, menu);
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

    public static void showAddTimeAlert(final ArrayList<DayOfWeek> days, ActionBarActivity activity ) {
        boolean finishParent = false;

        if (activity == null) {
            activity = act;
        }
        else {
            finishParent = true;
        }

        LinearLayout horizontal1 = new LinearLayout(activity);
        LinearLayout horizontal2 = new LinearLayout(activity);

        horizontal1.setOrientation(LinearLayout.HORIZONTAL);

        horizontal1.setLayoutParams(new RelativeLayout.LayoutParams(1000, 300));

        horizontal2.setOrientation(LinearLayout.HORIZONTAL);

        DataPicker dpLeft = new DataPicker(activity);
        DataPicker dpRight = new DataPicker(activity);

        DataPicker dpLeft2 = new DataPicker(activity);
        DataPicker dpRight2 = new DataPicker(activity);

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


        horizontal1.addView(new TimePicker(activity));


        horizontal2.addView(new TimePicker(activity));
        horizontal2.addView(new TimePicker(activity));



        LinearLayout vertical = new LinearLayout(activity);
        vertical.setOrientation(LinearLayout.HORIZONTAL);
        vertical.setLayoutParams(new RelativeLayout.LayoutParams(300, 500));


        final TimePicker picker = new TimePicker(activity);
        picker.setIs24HourView(true);
        vertical.addView(picker);

        TextView toTextView = new TextView(activity);
        toTextView.setText("");

        toTextView.setGravity(Gravity.CENTER_VERTICAL);
        vertical.addView(toTextView);

        final TimePicker picker2 = new TimePicker(activity);
        picker2.setIs24HourView(true);
        vertical.addView(picker2);
        final boolean finish = finishParent;
        final ActionBarActivity act = activity;

        new AlertDialog.Builder(activity)
        .setTitle("Add time interval")
        .setView(vertical)
        .setCancelable(true)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        Time t1 = new Time(picker.getCurrentHour(), picker.getCurrentMinute(), 0);
                        Time t2 = new Time(picker2.getCurrentHour(), picker2.getCurrentMinute(), 0);
                        for (int i = 0; i < days.size(); i++) {
                            Storage.boiler.timeTable.addSpan(days.get(i), t1, t2);
                        }
                        Storage.adapter.setNewContext(Storage.boiler.timeTable.getGroups());

                        if (finish) {
                            act.finish();
                        }
                    }
                })

        .show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Switch switcher = (Switch)findViewById(R.id.switch3);
        switcher.setChecked(false);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("MyLog", "onStop()");

        Storage.saveBoiler();
    }
}
