package com.mobitechs.woodsnipe;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;

import com.mobitechs.woodsnipe.adapter.Bday_Yesterday_Adaper;
import com.mobitechs.woodsnipe.model.BDay_Member_Items;
import com.mobitechs.woodsnipe.webService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BDay_Today_Tab extends Fragment implements View.OnClickListener {

    ProgressDialog progressDialog;
    List<BDay_Member_Items> listItems = new ArrayList<BDay_Member_Items>();
    Bday_Yesterday_Adaper adapter;
    RecyclerView recyclerView;
    String userId;
    String schoolId;
    LinearLayoutManager linearLayoutManager;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    String bDate="";
    private View v;
    private String method;
    public String todayBdayResponse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.bday_list, container, false);

        Animation animation = new TranslateAnimation(0f, 0f, 200f, 0f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setInterpolator(new BounceInterpolator());
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);

        bDate = dateFormat.format(cal.getTime());

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, true));

        adapter = new Bday_Yesterday_Adaper(listItems);
        recyclerView.setAdapter(adapter);

//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Please Wait");
//        progressDialog.show();


        int countNo = 1;
        String name = "Admin";
        String contact = "8655883062";
        String empId = "1";
        String image = "http://studentcares.net/Content/Images/SchoolPanel/StaffPhotos/40003.png";

        for (int count = 0; count < countNo; count++) {
            BDay_Member_Items items = new BDay_Member_Items();

            items.setname(name);
            items.setempId(empId);
            items.setempImage(image);
            items.setcontactNo(contact);
            listItems.add(items);

        }
        adapter.notifyDataSetChanged();
       // getList();
    }

    private void getList() {
        BdayListWebservice task = new BdayListWebservice();
        task.execute();
    }

    public class BdayListWebservice extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            method = "GetBDayDetails";
            todayBdayResponse = WebService.TodaysBday(bDate, method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (todayBdayResponse.equals("BDay List not Available.")) {
//                progressDialog.dismiss();

            } else if (todayBdayResponse.equals("No Network Found")) {
//                progressDialog.dismiss();

            } else {
//                progressDialog.dismiss();

                try {
                    JSONArray jArr = new JSONArray(todayBdayResponse);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        BDay_Member_Items items = new BDay_Member_Items();

                        items.setname(obj.getString("Name"));
                        items.setempId(obj.getString("empId"));
                        items.setempImage(obj.getString("image"));
                        items.setcontactNo(obj.getString("contactNo"));

                        listItems.add(items);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onClick(View v) {

    }

}
