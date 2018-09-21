package com.mobitechs.woodsnipe;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mobitechs.woodsnipe.adapter.AdapterForSpinner;
import com.mobitechs.woodsnipe.imageSelect.Image;
import com.mobitechs.woodsnipe.sessionManager.SessionManager;
import com.mobitechs.woodsnipe.webService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class School_Details extends BaseActivity implements View.OnClickListener, Image.OnRecyclerSetImageListener, CompoundButton.OnCheckedChangeListener {

    private ProgressDialog progressDialog = null;
    private ProgressDialog progressDialog2 = null;
    private FusedLocationProviderClient mFusedLocationClient;
    double latitude, longitude;
    String latLong = "";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public String method, responseResultCheckIn, responseResultPunchOut;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    String reminderDate;

    LinearLayout layoutReminderDateRemark;

    CheckBox chkIsNextVisit;
    //    Spinner spinnerStatus;
    Spinner spinnerTypes, spinnerBoard, spinnerCategory, spinnerSampleOfMonth, spinnerOrderOfMonth;
    EditText  txtRemark, txtContactPerson, txtEmail, txtContactNo, txtStrengthOfClass, txtComment;//txtResponse

    TextView txtReminderDate;
//    EditText txtCommonRemark;
//    Button btnSchoolCheckIn;
//    RelativeLayout imgLayout;
//    ImageView img;
//    Button btnAddImage;
    String firstImagePath = "";

    private StringBuilder date;
    Image image;

    String statusName;
    String statusId;
    private String[] statusArrayList;
    private AdapterForSpinner spinnerAdapter;
    private ProgressDialog standardDialogBox;
    private List<String> statusIdList = new ArrayList<String>();
    private List<String> statusNameList = new ArrayList<String>();

    String typesId;
    private String[] typesArrayList;
    private List<String> typesIdList = new ArrayList<String>();
    private List<String> typesNameList = new ArrayList<String>();

    String boardId;
    private String[] boardArrayList;
    private List<String> boardIdList = new ArrayList<String>();
    private List<String> boardNameList = new ArrayList<String>();

    String categoryId;
    private String[] categoryArrayList;
    private List<String> categoryIdList = new ArrayList<String>();
    private List<String> categoryNameList = new ArrayList<String>();

    String sampleOfMonthId;
    private String[] sampleOfMonthArrayList;
    private List<String> sampleOfMonthIdList = new ArrayList<String>();
    private List<String> sampleOfMonthNameList = new ArrayList<String>();

    String orderOfMonthId;
    private String[] orderOfMonthArrayList;
    private List<String> orderOfMonthIdList = new ArrayList<String>();
    private List<String> orderOfMonthNameList = new ArrayList<String>();

    Spinner spinnerStatusOfCall;
    String statusOfCallId;
    private String[] statusOfCallArrayList;
    private List<String> statusOfCallIdList = new ArrayList<String>();
    private List<String> statusOfCallNameList = new ArrayList<String>();

    Spinner spinnerReasonForVisit;
    String resonForVisitId;
    private String[] resonForVisitArrayList;
    private List<String> resonForVisitIdList = new ArrayList<String>();
    private List<String> resonForVisitNameList = new ArrayList<String>();

    RadioGroup  rgResponse;//rg1
    CheckBox radioTS, radioLS, radioDP,radioG,radioC,radioSB,radioNB;
    RadioButton radioInterested, radioNotInterested;

    String userId, schoolId = "", schoolCheckInId, imFrom = "";
    String typesName, boardName, categoryName, sampleOfMonthName, orderOfMonthName, statusOfCallName, resonForVisitName;
    String selectedReminderDate = "", remark, response, email, contactNo, contactPerson, strengthOfClass, comment,  radioValueTsTl;
    boolean isIntrested,isNextVisit;
    Button btnSubmit;

    EditText input;

    public School_Details() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_details);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getCurrentLocation();

        imFrom = "PunchIn";
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> typeOfUser = sessionManager.getUserDetails();
        userId = typeOfUser.get(SessionManager.KEY_USERID);

        Intent intent = getIntent();
        if (null != intent) {
            schoolId = intent.getStringExtra("schoolId");
        }

//        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        spinnerTypes = (Spinner) findViewById(R.id.spinnerTypes);
//        txtResponse = (EditText) findViewById(R.id.txtResponse);
        txtContactPerson = (EditText) findViewById(R.id.txtContactPerson);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtContactNo = (EditText) findViewById(R.id.txtContactNo);
        spinnerBoard = (Spinner) findViewById(R.id.spinnerBoard);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        spinnerSampleOfMonth = (Spinner) findViewById(R.id.spinnerSampleOfMonth);
        spinnerOrderOfMonth = (Spinner) findViewById(R.id.spinnerOrderOfMonth);
        spinnerReasonForVisit = (Spinner) findViewById(R.id.spinnerReasonForVisit);

//        rg1 = (RadioGroup) findViewById(R.id.rg1);
        radioTS = (CheckBox) findViewById(R.id.radioTS);
        radioLS = (CheckBox) findViewById(R.id.radioLS);
        radioDP = (CheckBox) findViewById(R.id.radioDP);
        radioG = (CheckBox) findViewById(R.id.radioG);
        radioC = (CheckBox) findViewById(R.id.radioC);
        radioSB = (CheckBox) findViewById(R.id.radioSB);
        radioNB = (CheckBox) findViewById(R.id.radioNB);

        rgResponse = (RadioGroup) findViewById(R.id.rgResponse);
        radioInterested = (RadioButton) findViewById(R.id.radioInterested);
        radioNotInterested = (RadioButton) findViewById(R.id.radioNotInterested);

        spinnerStatusOfCall = (Spinner) findViewById(R.id.spinnerStatusOfCall);

        txtStrengthOfClass = (EditText) findViewById(R.id.txtStrengthOfClass);
        txtComment = (EditText) findViewById(R.id.txtComment);

        chkIsNextVisit = (CheckBox) findViewById(R.id.chkIsNextVisit);
        txtReminderDate = (TextView) findViewById(R.id.txtReminderDate);
        txtRemark = (EditText) findViewById(R.id.txtRemark);

        layoutReminderDateRemark = (LinearLayout) findViewById(R.id.layoutReminderDateRemark);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
//        btnSchoolCheckIn = (Button) findViewById(R.id.btnSchoolCheckIn);
//        imgLayout = (RelativeLayout) findViewById(R.id.imgLayout);
//        btnAddImage = (Button) findViewById(R.id.btnAddImage);
//        img = (ImageView) findViewById(R.id.img);

        layoutReminderDateRemark.setVisibility(View.GONE);

        btnSubmit.setOnClickListener(this);
//        btnAddImage.setOnClickListener(this);
//        btnSchoolCheckIn.setOnClickListener(this);
        txtReminderDate.setOnClickListener(this);
        chkIsNextVisit.setOnCheckedChangeListener(this);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        String currMonth = String.valueOf(month + 1);
        reminderDate = day + "-" + currMonth + "-" + year;
        txtReminderDate.setText(reminderDate);

//        txtResponse.setFocusable(false);

//        getStatusSpinnerList();
        getTypesSpinnerList();
        getBoardSpinnerList();
        getCategorySpinnerList();
        getSalesOfMonthSpinnerList();
        getOrderOfMonthSpinnerList();
        getReasonForVisitSpinnerList();
        getStatusOfCallSpinnerList();

//        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
//                boolean isChecked = checkedRadioButton.isChecked();
//                String radioName = String.valueOf(checkedRadioButton.getText());
//
//                if (radioName.equals("TS")) {
//                    radioValueTsTl = radioName;
//                } else if (radioName.equals("LS")) {
//                    radioValueTsTl = radioName;
//                } else if (radioName.equals("AP")) {
//                    radioValueTsTl = radioName;
//                } else if (radioName.equals("DP")) {
//                    radioValueTsTl = radioName;
//                }
//            }
//        });
        rgResponse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                String radioName = String.valueOf(checkedRadioButton.getText());

                if (radioName.equals("Interested")) {
                    isIntrested = true;
                } else if (radioName.equals("Not Interested")) {
                    isIntrested = false;
                }
            }
        });

    }

    private void getStatusOfCallSpinnerList() {
//        orderOfMonthArrayList = new String[]{
//                "Select Order Of Month"
//        };
//        orderOfMonthNameList = new ArrayList<>(Arrays.asList(orderOfMonthArrayList));
//        spinnerAdapter = new AdapterForSpinner(this, R.layout.spinneritem, orderOfMonthNameList);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerOrderOfMonth.setAdapter(spinnerAdapter);
//        spinnerOrderOfMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position > 0) {
//                    orderOfMonthName = parent.getItemAtPosition(position).toString();
////                    orderOfMonthId = orderOfMonthIdList.get(position);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        spinnerStatusOfCall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                statusOfCallName = spinnerStatusOfCall.getSelectedItem().toString();
//                Toast.makeText(getBaseContext(), spinnerOrderOfMonth.getSelectedItem().toString(),
//                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void getReasonForVisitSpinnerList() {
        spinnerReasonForVisit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                resonForVisitName = spinnerReasonForVisit.getSelectedItem().toString();
//                Toast.makeText(getBaseContext(), spinnerOrderOfMonth.getSelectedItem().toString(),
//                        Toast.LENGTH_SHORT).show();
                if(resonForVisitName.equals("Other")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(School_Details.this);
                    builder.setTitle("Enter Reason");

                    int margin = (int) getResources().getDimension(R.dimen.margin);
                    RelativeLayout layout = new RelativeLayout(School_Details.this);
                    layout.setLayoutParams(new RelativeLayout.LayoutParams(
                            CoordinatorLayout.LayoutParams.MATCH_PARENT,
                            CoordinatorLayout.LayoutParams.WRAP_CONTENT));

                    final EditText input = new EditText(School_Details.this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    input.setLayoutParams(params);
                    params.setMargins(margin, margin, margin, margin);

                    input.setHint("Enter reason");
                    input.setBackground(getResources().getDrawable(R.drawable.border_rectangle));
                    layout.addView(input);
                    builder.setView(layout);

                    builder.create();
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface alert, int which) {
                            // TODO Auto-generated method stub
                            //Do something
                            String reasonForVisitOther = input.getText().toString();
                            if(reasonForVisitOther.equals("")){
                                Toast.makeText(School_Details.this, "Please Enter Reason.", Toast.LENGTH_SHORT).show();
                            }else{
                                //get value of reason
                                alert.dismiss();
                            }

                        }
                    });

                    builder.show();
                }
            }



            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void getTypesSpinnerList() {
        spinnerTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                typesName = spinnerTypes.getSelectedItem().toString();
//                Toast.makeText(getBaseContext(), spinnerTypes.getSelectedItem().toString(),
//                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void getBoardSpinnerList() {

        spinnerBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                boardName = spinnerBoard.getSelectedItem().toString();
//                Toast.makeText(getBaseContext(), spinnerBoard.getSelectedItem().toString(),
//                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void getCategorySpinnerList() {

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                categoryName = spinnerCategory.getSelectedItem().toString();
//                Toast.makeText(getBaseContext(), spinnerCategory.getSelectedItem().toString(),
//                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void getSalesOfMonthSpinnerList() {

        spinnerSampleOfMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                sampleOfMonthName = spinnerSampleOfMonth.getSelectedItem().toString();
//                Toast.makeText(getBaseContext(), spinnerSampleOfMonth.getSelectedItem().toString(),
//                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void getOrderOfMonthSpinnerList() {

        spinnerOrderOfMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                orderOfMonthName = spinnerOrderOfMonth.getSelectedItem().toString();
//                Toast.makeText(getBaseContext(), spinnerOrderOfMonth.getSelectedItem().toString(),
//                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

//    private void getStatusSpinnerList() {
//        statusArrayList = new String[]{
//                "Standard"
//        };
//        statusNameList = new ArrayList<>(Arrays.asList(statusArrayList));
//        spinnerAdapter = new AdapterForSpinner(this, R.layout.spinneritem, statusNameList);
//        getListOfStandard();
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerStatus.setAdapter(spinnerAdapter);
//        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position > 0) {
//                    statusName = parent.getItemAtPosition(position).toString();
//                    statusId = statusIdList.get(position);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }
//
//    private void getListOfStandard() {
////        Get_StdDivSub_Sqlite standardSpinnerList = new Get_StdDivSub_Sqlite(this);
////        standardSpinnerList.FetchAllstandard(statusNameList, statusIdList, schoolId, spinnerAdapter);
//    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//        if (chkIsNextVisit.isChecked()) {
        if (isChecked) {
            isNextVisit = true;
            layoutReminderDateRemark.setVisibility(View.VISIBLE);

        } else {
            isNextVisit = false;
            layoutReminderDateRemark.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {

//        if (v.getId() == R.id.btnCheckIn) {
//            progressDialog = new ProgressDialog(this);
//            progressDialog.setMessage("Punching In.");
//            progressDialog.show();
//            getCurrentLocation();
//        }
        if (v.getId() == R.id.btnSubmit) {

            HashMap<String, String> getAttId = sessionManager.getSchoolCheckInId();
            schoolCheckInId = getAttId.get(SessionManager.KEY_SCHOOL_CHECKINID);

            schoolCheckInId = "1";

//            response = txtResponse.getText().toString();
            contactPerson = txtContactPerson.getText().toString();
            email = txtEmail.getText().toString();
            contactNo = txtContactNo.getText().toString();
            strengthOfClass = txtStrengthOfClass.getText().toString();
            comment = txtComment.getText().toString();
            remark = txtRemark.getText().toString();
            selectedReminderDate = txtReminderDate.getText().toString();

            if(contactPerson.equals("")){
                Toast.makeText(this, "Please Enter Contact Person.", Toast.LENGTH_SHORT).show();
            }
            else if(email.equals("")){
                Toast.makeText(this, "Please Enter Email Id.", Toast.LENGTH_SHORT).show();
            }
            else if(contactNo.equals("")){
                Toast.makeText(this, "Please Enter Contact No.", Toast.LENGTH_SHORT).show();
            }
            else{
                imFrom = "SubmitBtn";
                getCurrentLocation();
            }


        } else if (v.getId() == R.id.txtReminderDate) {

            DatePickerDialog mDatePicker = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                    selectedReminderDate = String.valueOf(selectedyear) + "-" + String.valueOf(selectedmonth + 1) + "-" + String.valueOf(selectedday);
                    txtReminderDate.setText(selectedReminderDate);

                }
            }, year, month, day);
            mDatePicker.show();
        }
//        else if (v.getId() == R.id.btnAddImage) {
//            image = new Image(this, "SchoolDetails", this);
//            image.getImage();
//        }
    }

    private void getCurrentLocation() {
        checkLocationPermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                            latLong = String.valueOf(latitude) + "-" + String.valueOf(longitude);


                            if (imFrom.equals("PunchIn")) {
                                progressDialog = new ProgressDialog(School_Details.this);
                                progressDialog.setMessage("Please Wait..");
                                progressDialog.show();
                                progressDialog.setCancelable(false);
                                progressDialog.setCanceledOnTouchOutside(false);

                                SchoolSchoolCheckInWebService task = new SchoolSchoolCheckInWebService();
                                task.execute();
                            } else if (imFrom.equals("SubmitBtn")) {
                                progressDialog2 = new ProgressDialog(School_Details.this);
                                progressDialog2.setMessage("Please Wait..");
                                progressDialog2.show();
                                progressDialog2.setCancelable(false);
                                progressDialog2.setCanceledOnTouchOutside(false);

                                SchoolSchoolCheckOutWebService task = new SchoolSchoolCheckOutWebService();
                                task.execute();
                            }
                        }
                    }
                });
    }

    @Override
    @TargetApi(23)
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        image.getActivityResult(requestCode, resultCode, intent);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        image.getRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

    @Override
    public void onRecyclerImageSet(Bitmap imageToShow, String imageBase64String) {
//        imgLayout.setVisibility(View.VISIBLE);
//        img.setImageBitmap(imageToShow);
//        btnAddImage.setText("Change Image");
//        this.firstImagePath = imageBase64String;
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
                                ActivityCompat.requestPermissions(School_Details.this,
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
                    getCurrentLocation();
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        getCurrentLocation();
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

    public class SchoolSchoolCheckInWebService extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            method = "SchoolCheckin";
            responseResultCheckIn = WebService.SchoolCheckIn(userId, schoolId, latLong, method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (responseResultCheckIn.equals("AllReady Checked IN")) {
                progressDialog.dismiss();
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(School_Details.this);
                builder.setTitle("Result");
                builder.setMessage(responseResultCheckIn);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                android.support.v7.app.AlertDialog alert1 = builder.create();
                alert1.show();
            } else if (responseResultCheckIn.equals("No Network Found")) {
                progressDialog.dismiss();
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(School_Details.this);
                builder.setTitle("Result");
                builder.setMessage("Unable To Check In. Please Try Again Later.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                android.support.v7.app.AlertDialog alert1 = builder.create();
                alert1.show();
            } else {

                progressDialog.dismiss();
                sessionManager = new SessionManager(School_Details.this);
                try {
//                    JSONObject obj = new JSONObject(responseResultLocation);

                    JSONArray jArr = new JSONArray(responseResultCheckIn);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        schoolCheckInId = obj.getString("SchoolCheckIn1");
//
//                        Toast.makeText(School_Details.this, "Id: " + schoolCheckInId, Toast.LENGTH_SHORT).show();
                        sessionManager.SetSchoolCheckInId(schoolId, schoolCheckInId);
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    public class SchoolSchoolCheckOutWebService extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            method = "SchoolCheckinDetails";
            responseResultCheckIn = WebService.SchoolCheckOut(userId, schoolId, schoolCheckInId, typesName, response, contactPerson, email, contactNo, boardName, categoryName, strengthOfClass, sampleOfMonthName, orderOfMonthName, resonForVisitName, radioValueTsTl, isIntrested, statusOfCallName, comment, isNextVisit, selectedReminderDate, remark, String.valueOf(latitude), String.valueOf(longitude), method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {

            if (responseResultCheckIn.equals("No Network Found")) {
                progressDialog2.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(School_Details.this);
                builder.setTitle("Result");
                builder.setMessage("Unable To Submit. Please Try Again Later.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();
            } else {

                progressDialog2.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(School_Details.this);
                builder.setTitle("Result");
                builder.setMessage("Successfully Submitted.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        launchIntent();

                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();

            }
        }
    }


    private void launchIntent() {
        Intent it = new Intent(School_Details.this, School_List.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
    }
}
