package com.mobitechs.woodsnipe.adapter;


import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobitechs.woodsnipe.Login;
import com.mobitechs.woodsnipe.Reason_For_Visit;
import com.mobitechs.woodsnipe.School_Details;

import java.util.ArrayList;
import java.util.List;

public class AdapterForSpinner extends ArrayAdapter<String> {

    private ArrayList arrayList;
    private Context context;

    public AdapterForSpinner(Reason_For_Visit rfv, int spinneritem, List<String> nameList) {
        super(rfv,spinneritem,nameList);
    }

    public AdapterForSpinner(School_Details school_details, int spinneritem, List<String> standardNameList) {
        super(school_details,spinneritem,standardNameList);
    }


    @Override
    public boolean isEnabled(int position) {
        if(position == 0) {
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {

        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if(position == 0){
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
        else {
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
        return view;
    }
}