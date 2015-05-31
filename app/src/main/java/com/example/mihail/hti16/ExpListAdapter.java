package com.example.mihail.hti16;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mihail.hti16.Boiler.Boiler;
import com.example.mihail.hti16.Boiler.DayOfWeek;
import com.example.mihail.hti16.Boiler.Storage;

import java.util.ArrayList;

public class ExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<String>> mGroups;
    private Context mContext;

    public ExpListAdapter(Context context, ArrayList<ArrayList<String>> groups){
        mContext = context;
        mGroups = groups;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_view, null);
        }

        if (isExpanded){

        }
        else{

        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        textGroup.setText(DayOfWeek.at(groupPosition));

        return convertView;

    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_view, null);
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
        final  Button button = (Button)convertView.findViewById(R.id.buttonChild);

        final ImageView image = (ImageView)convertView.findViewById(R.id.imageView);
        int id = convertView.getResources().getIdentifier("sun", "drawable", Storage.PACKAGE_NAME);
        image.setImageResource(id);


        textChild.setText(mGroups.get(groupPosition).get(childPosition));
        if(childPosition+1 < this.getChildrenCount(groupPosition)) {
            textChild.setTextColor(Color.BLACK);
            textChild.setTextSize(12);
            image.setVisibility(View.VISIBLE);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    button.setVisibility(View.VISIBLE);
                    image.setVisibility(View.INVISIBLE);
                }

            });
        }
        else
        {
            textChild.setTextColor(Color.RED);
            textChild.setTextSize(20);
            image.setVisibility(View.INVISIBLE);
            final int pos = groupPosition + 1;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DayOfWeek day = DayOfWeek.of(pos);
                    ArrayList<DayOfWeek> ar= new ArrayList<DayOfWeek>();
                    ar.add(day);
                    TimeTableShowerActivity.showAddTimeAlert(ar, null);

                }

            });
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Storage.boiler.timeTable.remove(groupPosition,childPosition);
                ExpListAdapter.this.setNewContext(Storage.boiler.timeTable.getGroups());
                button.setVisibility(View.INVISIBLE);
            }
        });





        return convertView;
    }



    public void setNewContext(ArrayList<ArrayList<String>> cont) {
        this.mGroups = cont;
        this.notifyDataSetChanged();
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}