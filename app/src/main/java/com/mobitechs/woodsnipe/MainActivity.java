package com.mobitechs.woodsnipe;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mobitechs.woodsnipe.adapter.Birthday_Pager_Adapter;
import com.mobitechs.woodsnipe.sessionManager.SessionManager;
import com.mobitechs.woodsnipe.webService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    Button btnCheckIn, btnCheckOut;
    private FusedLocationProviderClient mFusedLocationClient;
    // TextView txtLatLong;

    double latitude, longitude;
    int locationCounter = 0;

    private String method;
    public String responseResultPunchIn, responseResultPunchOut, responseResultLocation;
    String latLong = "";
    String userId = "", attendanceId = "", punchInTime = "", punchOutTime = "", imFrom = "", punchInMsg = "", punchOutMsg = "";

    Timer timer;
    Handler handler = new Handler();
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private ProgressDialog progressDialog = null;
    TextView txtPunchInTime, txtPunchOutTime;
    String title ="",msg ="",dialogFor;
    Drawable icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        icon = getResources().getDrawable(R.drawable.ic_watch_orange_24dp);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermission();

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> typeOfUser = sessionManager.getUserDetails();
        userId = typeOfUser.get(SessionManager.KEY_USERID);

        HashMap<String, String> getAttId = sessionManager.GetCheckinId();
        punchInTime = getAttId.get(SessionManager.KEY_PUNCHIN_TIME);
        punchOutTime = getAttId.get(SessionManager.KEY_PUNCHOUT_TIME);

        btnCheckIn = (Button) findViewById(R.id.btnCheckIn);
        btnCheckOut = (Button) findViewById(R.id.btnCheckOut);
        // txtLatLong = (TextView) findViewById(R.id.txtLatLong);
        txtPunchInTime = (TextView) findViewById(R.id.txtPunchInTime);
        txtPunchOutTime = (TextView) findViewById(R.id.txtPunchOutTime);

        btnCheckIn.setOnClickListener(this);
        btnCheckOut.setOnClickListener(this);

        CheckInOrNotWebService taskPunch = new CheckInOrNotWebService();
        taskPunch.execute();


//        if (punchInTime == null) {
//            punchInTime = "";
//        }
//        if (punchOutTime == null) {
//            punchOutTime = "";
//        }
//
//        if (!punchInTime.equals("")) {
//            txtPunchInTime.setVisibility(View.VISIBLE);
//            txtPunchInTime.setText(punchInTime);
//            btnCheckIn.setBackgroundColor(getResources().getColor(R.color.btnBg));
//        }
////        else{
////            txtPunchInTime.setVisibility(View.GONE);
////        }
//        if (!punchOutTime.equals("")) {
//            txtPunchOutTime.setVisibility(View.VISIBLE);
//            txtPunchOutTime.setText(punchOutTime);
//            btnCheckOut.setBackgroundColor(getResources().getColor(R.color.btnBg));
//        }
//        else{
//            txtPunchOutTime.setVisibility(View.GONE);
//        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Yesterday"));
        tabLayout.addTab(tabLayout.newTab().setText("Today"));
        tabLayout.addTab(tabLayout.newTab().setText("Tomorrow"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        Birthday_Pager_Adapter adapter = new Birthday_Pager_Adapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCheckIn) {

            dialogFor="UserCheckInConfirmation";
            msg = "Do You Want To Punch In ?";
            ShowConfirmationDialog(dialogFor,msg,title);

        } else if (v.getId() == R.id.btnCheckOut) {

            imFrom="PunchOut";
            dialogFor ="UserCheckOutConfirmation";
            msg = "Do You Want To Punch Out ?";
            ShowConfirmationDialog(dialogFor,msg,title);
        }
    }

    private void ShowConfirmationDialog(final String dialogFor, String msg, String title) {

        if(dialogFor.equals("APIError")){
            icon = getResources().getDrawable(R.drawable.ic_info_outline_orange_24dp);
        }
        new MaterialStyledDialog.Builder(this)
               // .setTitle(title)
                .setHeaderColor(R.color.colorPrimaryDark)
                .setDescription(msg)
                .setPositiveText("Ok")
                .setIcon(icon)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        if(dialogFor.equals("UserCheckInConfirmation")){
                            progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setMessage("Punching In.");
                            progressDialog.show();

                            imFrom = "PunchIn";
                            getCurrentLocation(imFrom);
                            UpdateLocationTimer();
                        }
                        else if(dialogFor.equals("UserCheckOutConfirmation")){

                            HashMap<String, String> getAttId = sessionManager.GetCheckinId();
                            attendanceId = getAttId.get(SessionManager.KEY_ATTENDANCEID);
                            progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setMessage("Punching Out.");
                            progressDialog.show();

                            imFrom = "PunchOut";
                            getCurrentLocation(imFrom);

                            if (timer != null) {
                                timer.cancel();
                            }

                            if (attendanceId == null) {
                                attendanceId = "";
                            }
                        }
                    }
                })
                .show();

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.support.v7.app.AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        getCurrentLocation(imFrom);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    private void UpdateLocationTimer() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        locationCounter++;
                        imFrom = "UpdateTimer";
                        getCurrentLocation(imFrom);
                    }
                });
            }
        };

        timer.schedule(timerTask, 0, 900000); //15 minute //15 seconds 15000

    }

    private void getCurrentLocation(final String imFrom) {
        checkLocationPermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            // txtLatLong.setText("Location Tracking Started" + "\n Latitude : " + latitude + "\n Longitude: " + longitude+ "\n Counter: " + locationCounter);

                            latLong = String.valueOf(latitude) + "-" + String.valueOf(longitude);

                            if (imFrom.equals("UpdateTimer")) {
                                UserLocationWebService task = new UserLocationWebService();
                                task.execute();
                            }
                            else if (imFrom.equals("PunchIn")) {
                                CheckInWebService task = new CheckInWebService();
                                task.execute();
                            }
                            else if (imFrom.equals("PunchOut")) {
                                CheckOutWebService task = new CheckOutWebService();
                                task.execute();
                            }
                        }
                    }
                });
    }

    public class CheckInOrNotWebService extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            method = "CheckPunchin";
            responseResultPunchIn = WebService.PunchINOrNot(userId, method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (responseResultPunchIn.equals("No Network Found")) {
                title="Response";
                dialogFor ="APIError";
                msg = "Unable To Find Punching Details. Please Try Again Later.";
                ShowConfirmationDialog(dialogFor,msg,title);
            }
            else {
                sessionManager = new SessionManager(MainActivity.this);
                try {
                    JSONArray jArr = new JSONArray(responseResultPunchIn);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        attendanceId = obj.getString("AttendanceID");
                        punchInTime = obj.getString("PunchIn");
                        punchOutTime = obj.getString("PunchOut");
                        punchInMsg = obj.getString("msg");//Already Punch In //Not Punch In

                        if (punchInMsg.equals("Punch In")) {

                            txtPunchInTime.setVisibility(View.VISIBLE);
                            txtPunchOutTime.setVisibility(View.VISIBLE);
                            txtPunchInTime.setText(punchInTime);
                            txtPunchOutTime.setText(punchOutTime);

                            btnCheckIn.setBackgroundColor(getResources().getColor(R.color.btnBg));

                            if(punchOutTime.equals("")){
                                btnCheckOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            }else{
                                btnCheckOut.setBackgroundColor(getResources().getColor(R.color.btnBg));
                            }
                        }
                        else {
                            txtPunchInTime.setVisibility(View.GONE);
                            txtPunchOutTime.setVisibility(View.GONE);

                            btnCheckOut.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            btnCheckIn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public class CheckInWebService extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            method = "PunchIN";
            responseResultPunchIn = WebService.PunchIN(userId, latLong, method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (responseResultPunchIn.equals("No Network Found")) {
                progressDialog.dismiss();

                title="Response";
                dialogFor ="APIError";
                msg = "Unable To Punch In. Please Try Again Later.";
                ShowConfirmationDialog(dialogFor,msg,title);
            }
            else {
                progressDialog.dismiss();
                sessionManager = new SessionManager(MainActivity.this);
                try {
                    JSONArray jArr = new JSONArray(responseResultPunchIn);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        attendanceId = obj.getString("AttendanceID");
                        punchInTime = obj.getString("PunchIn");
                        punchOutTime = obj.getString("PunchOut");
                        punchInMsg = obj.getString("msg");

                        if (punchInMsg.equals("Punch In Successfully.")) {
                            dialogFor ="PunchInSuccessFull";
                            msg = "You Have Punched In Successfully at "+punchInTime+".";
                            ShowConfirmationDialog(dialogFor,msg,title);
                        }
                        else if (punchInMsg.equals("Punch In Already Exist.")) {
                            dialogFor ="PunchInSuccessFull";
                            msg = "You Have Already Punched In at "+punchInTime+".";
                            ShowConfirmationDialog(dialogFor,msg,title);
                        }
                        txtPunchInTime.setVisibility(View.VISIBLE);
                        txtPunchInTime.setText(punchInTime);
                        btnCheckIn.setBackgroundColor(getResources().getColor(R.color.btnBg));
                        sessionManager.SetCheckinId(attendanceId, punchInTime, punchOutTime);

                        if(!punchOutTime.equals("")){
                            txtPunchOutTime.setVisibility(View.VISIBLE);
                            txtPunchOutTime.setText(punchOutTime);
                            btnCheckOut.setBackgroundColor(getResources().getColor(R.color.btnBg));
                            sessionManager.SetCheckinId(attendanceId, punchInTime, punchOutTime);
                        }
                    }
                    txtPunchOutTime.setText(punchInTime);
                    txtPunchOutTime.setText(punchOutTime);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    public class CheckOutWebService extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            method = "PunchOut";
            responseResultPunchOut = WebService.PunchOut(userId, attendanceId, method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (responseResultPunchOut.equals("No Network Found")) {
                progressDialog.dismiss();

                title="Response";
                dialogFor ="APIError";
                msg = "Unable To Punch Out. Please Try Again Later.";
                ShowConfirmationDialog(dialogFor,msg,title);

            }
            else {

                progressDialog.dismiss();
                sessionManager = new SessionManager(MainActivity.this);
                try {
//                    JSONObject obj = new JSONObject(responseResultLocation);

                    JSONArray jArr = new JSONArray(responseResultPunchOut);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        attendanceId = obj.getString("AttendanceID");
                        punchInTime = obj.getString("PunchIn");
                        punchOutTime = obj.getString("PunchOut");
                        punchOutMsg = obj.getString("msg");

                        if (punchOutMsg.equals("Punch Out Successfully.")) {

                            dialogFor ="PunchOutSuccessFull";
                            msg = "You Have Punched Out Successfully at "+punchOutTime+".";
                            ShowConfirmationDialog(dialogFor,msg,title);

                        }
//                        else if (punchOutMsg.equals("Punch In Already Exist.")) {
//                            dialogFor ="PunchOutSuccessFull";
//                            msg = "You Have Already Punched Out at "+punchOutTime+".";
//                            ShowConfirmationDialog(dialogFor,msg,title);
//                        }
                        txtPunchOutTime.setVisibility(View.VISIBLE);
                        txtPunchOutTime.setText(punchOutTime);
                        btnCheckOut.setBackgroundColor(getResources().getColor(R.color.btnBg));
                        sessionManager.SetCheckinId(attendanceId, punchInTime, punchOutTime);

                        if(!punchInTime.equals("")){
                            txtPunchInTime.setVisibility(View.VISIBLE);
                            txtPunchInTime.setText(punchInTime);
                            btnCheckIn.setBackgroundColor(getResources().getColor(R.color.btnBg));
                            sessionManager.SetCheckinId(attendanceId, punchInTime, punchOutTime);
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public class UserLocationWebService extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            method = "EmpTrack";
            responseResultLocation = WebService.SaveUserLocation(userId, String.valueOf(latitude), String.valueOf(longitude), method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {

        }
    }

}
