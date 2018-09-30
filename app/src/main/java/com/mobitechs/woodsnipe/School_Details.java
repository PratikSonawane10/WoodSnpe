package com.mobitechs.woodsnipe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mobitechs.woodsnipe.adapter.AdapterForSpinner;
import com.mobitechs.woodsnipe.adapter.Multiple_Images_Adapter;
import com.mobitechs.woodsnipe.connectivity.GetDepartMent;
import com.mobitechs.woodsnipe.imageSelect.Image;
import com.mobitechs.woodsnipe.sessionManager.SessionManager;
import com.mobitechs.woodsnipe.webService.WebService;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;

public class School_Details extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

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

    String seriesPrefrence = "", storyBook = "", reminderDate = "";

    LinearLayout layoutReminderDateRemark;

    CheckBox chkIsNextVisit, chkPrePrimary, chkPrimary, chkSecondary;
    //    Spinner spinnerStatus;
    Spinner spinnerTypes, spinnerBoard, spinnerCategory, spinnerSampleOfMonth, spinnerOrderOfMonth;
    EditText txtRemark, txtContactPerson, txtEmail, txtContactNo, txtStrengthOfClass, txtComment;//txtResponse

    TextView txtReminderDate;

    private StringBuilder date;
    Image image;

    String typesName = "", typesId;
    private String[] typesArrayList;
    private List<String> typesIdList = new ArrayList<String>();
    private List<String> typesNameList = new ArrayList<String>();

    String boardName = "", boardId;
    private String[] boardArrayList;
    private List<String> boardIdList = new ArrayList<String>();
    private List<String> boardNameList = new ArrayList<String>();

    String categoryName = "", categoryId;
    private String[] categoryArrayList;
    private List<String> categoryIdList = new ArrayList<String>();
    private List<String> categoryNameList = new ArrayList<String>();

    String sampleOfMonthName = "", sampleOfMonthId;
    private String[] sampleOfMonthArrayList;
    private List<String> sampleOfMonthIdList = new ArrayList<String>();
    private List<String> sampleOfMonthNameList = new ArrayList<String>();

    String orderOfMonthName = "", orderOfMonthId;
    private String[] orderOfMonthArrayList;
    private List<String> orderOfMonthIdList = new ArrayList<String>();
    private List<String> orderOfMonthNameList = new ArrayList<String>();

    Spinner spinnerStatusOfCall;
    String statusOfCallName = "", statusOfCallId;
    private String[] statusOfCallArrayList;
    private List<String> statusOfCallIdList = new ArrayList<String>();
    private List<String> statusOfCallNameList = new ArrayList<String>();

    private AdapterForSpinner spinnerAdapter;

    RadioGroup rgResponse;
    RadioButton radioInterested, radioNotInterested;

    String userId, schoolId = "", schoolName = "", schoolCheckInId, imFrom = "";

    String isIntrested = "", selectedReminderDate = "", remark, schoolSection = "", otherCustomerType = "", email, contactNo, contactPerson, strengthOfClass, comment;
    String isNextVisit;
    Button btnNext;

    EditText txtOther, txtTinyStepsQty, txtLittleStepsQty, txtComputerQty, txtCursive1, txtCursive2, txtCursive3, txtNoteBookQty, txtDrawingBookQty, txtScrapBookQty, txtGraphBookQty, txtSampleOther, txtSampleOtherQty, txtSampleContactPerson, txtSampleContactNo, txtSampleEmailId;
    FloatingActionButton fabSubmit;
    String title = "", msg = "", dialogFor, otherFor;
    String punchInMsg, responseResultPunchIn;
    Drawable icon;

    TextView txtSchoolTitle;
    LinearLayout schoolSectionLayout, otherLayout;

    private List<String> schoolSectionList = new ArrayList<String>();

    RadioGroup rgSeriesPref, rgStoryBook;
    RadioButton rbTs, rbLs, rbYes, rbNo, rbMayBe;

    public School_Details() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_details);

        icon = getResources().getDrawable(R.drawable.ic_watch_orange_24dp);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        imFrom = "PunchIn";
        getCurrentLocation();

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> typeOfUser = sessionManager.getUserDetails();
        userId = typeOfUser.get(SessionManager.KEY_USERID);

        Intent intent = getIntent();
        if (null != intent) {
            schoolId = intent.getStringExtra("schoolId");
            schoolName = intent.getStringExtra("schoolName");
        }

        rgSeriesPref = (RadioGroup) findViewById(R.id.rgSeriesPref);
        rgStoryBook = (RadioGroup) findViewById(R.id.rgStoryBook);
        rbTs = (RadioButton) findViewById(R.id.rbTs);
        rbLs = (RadioButton) findViewById(R.id.rbLs);
        rbYes = (RadioButton) findViewById(R.id.rbYes);
        rbNo = (RadioButton) findViewById(R.id.rbNo);
        rbMayBe = (RadioButton) findViewById(R.id.rbMayBe);


        txtSchoolTitle = (TextView) findViewById(R.id.txtSchoolTitle);
        spinnerTypes = (Spinner) findViewById(R.id.spinnerTypes);
        txtContactPerson = (EditText) findViewById(R.id.txtContactPerson);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtContactNo = (EditText) findViewById(R.id.txtContactNo);
        spinnerBoard = (Spinner) findViewById(R.id.spinnerBoard);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        spinnerSampleOfMonth = (Spinner) findViewById(R.id.spinnerSampleOfMonth);
        spinnerOrderOfMonth = (Spinner) findViewById(R.id.spinnerOrderOfMonth);

        txtOther = (EditText) findViewById(R.id.txtOther);

        rgResponse = (RadioGroup) findViewById(R.id.rgResponse);
        radioInterested = (RadioButton) findViewById(R.id.radioInterested);
        radioNotInterested = (RadioButton) findViewById(R.id.radioNotInterested);

        spinnerStatusOfCall = (Spinner) findViewById(R.id.spinnerStatusOfCall);

        txtStrengthOfClass = (EditText) findViewById(R.id.txtStrengthOfClass);
        txtComment = (EditText) findViewById(R.id.txtComment);

        chkIsNextVisit = (CheckBox) findViewById(R.id.chkIsNextVisit);
        chkPrePrimary = (CheckBox) findViewById(R.id.chkPrePrimary);
        chkPrimary = (CheckBox) findViewById(R.id.chkPrimary);
        chkSecondary = (CheckBox) findViewById(R.id.chkSecondary);

        txtReminderDate = (TextView) findViewById(R.id.txtReminderDate);
        txtRemark = (EditText) findViewById(R.id.txtRemark);

        layoutReminderDateRemark = (LinearLayout) findViewById(R.id.layoutReminderDateRemark);
        schoolSectionLayout = (LinearLayout) findViewById(R.id.schoolSectionLayout);
        otherLayout = (LinearLayout) findViewById(R.id.otherLayout);

        btnNext = (Button) findViewById(R.id.btnNext);

        layoutReminderDateRemark.setVisibility(View.GONE);
        schoolSectionLayout.setVisibility(View.GONE);
        otherLayout.setVisibility(View.GONE);

        btnNext.setOnClickListener(this);
        txtReminderDate.setOnClickListener(this);
        chkIsNextVisit.setOnCheckedChangeListener(this);

        txtSchoolTitle.setText(schoolName);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        String currMonth = String.valueOf(month + 1);
        reminderDate = day + "-" + currMonth + "-" + year;
        txtReminderDate.setText(reminderDate);

        getStatusOfCallSpinnerList();
        getTypesSpinnerList();
        getBoardSpinnerList();
        getCategorySpinnerList();
        getSalesOfMonthSpinnerList();
        getOrderOfMonthSpinnerList();

        rgResponse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                String radioName = String.valueOf(checkedRadioButton.getText());

                if (radioName.equals("Interested")) {
                    isIntrested = "Interested";
                } else if (radioName.equals("Not Interested")) {
                    isIntrested = "Not Interested";
                }
            }
        });
        rgSeriesPref.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                String radioName = String.valueOf(checkedRadioButton.getText());

                if (radioName.equals("TS")) {
                    seriesPrefrence = "TS";
                } else if (radioName.equals("LS")) {
                    seriesPrefrence = "LS";
                }
            }
        });
        rgStoryBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                String radioName = String.valueOf(checkedRadioButton.getText());

                if (radioName.equals("Yes")) {
                    storyBook = "Yes";
                } else if (radioName.equals("No")) {
                    storyBook = "No";
                } else if (radioName.equals("May Be")) {
                    storyBook = "May Be";
                }
            }
        });

        chkPrePrimary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkPrePrimary.isChecked()) {
                    schoolSectionList.add("Pre-Primary");
                } else {
                    schoolSectionList.remove("Pre-Primary");
                }
            }
        });
        chkPrimary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkPrimary.isChecked()) {
                    schoolSectionList.add("Primary");
                } else {
                    schoolSectionList.remove("Primary");
                }
            }
        });
        chkSecondary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkSecondary.isChecked()) {
                    schoolSectionList.add("Secondary");
                } else {
                    schoolSectionList.remove("Secondary");
                }
            }
        });
    }

    private void getStatusOfCallSpinnerList() {
        statusOfCallArrayList = new String[]{
                "Select Status"
        };
        statusOfCallNameList = new ArrayList<>(Arrays.asList(statusOfCallArrayList));
        spinnerAdapter = new AdapterForSpinner(this, R.layout.spinneritem, statusOfCallNameList);
        getListOfStatus();
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatusOfCall.setAdapter(spinnerAdapter);
        spinnerStatusOfCall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    statusOfCallName = parent.getItemAtPosition(position).toString();
                    statusOfCallId = statusOfCallIdList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getListOfStatus() {
        ProgressDialog spinnerDialogBox = ProgressDialog.show(this, "", "Fetching Status Please Wait...", true);
        GetDepartMent getDepartMent = new GetDepartMent(this);
        getDepartMent.FetchallStatusOfC(statusOfCallNameList, statusOfCallIdList, spinnerAdapter, spinnerDialogBox);
    }

    private void getTypesSpinnerList() {
        typesArrayList = new String[]{
                "Select Type"
        };
        typesNameList = new ArrayList<>(Arrays.asList(typesArrayList));
        spinnerAdapter = new AdapterForSpinner(this, R.layout.spinneritem, typesNameList);
        getListOfTypes();
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypes.setAdapter(spinnerAdapter);
        spinnerTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    typesName = parent.getItemAtPosition(position).toString();
                    typesId = typesIdList.get(position);

                    if (typesName.equals("School")) {
                        schoolSectionLayout.setVisibility(View.VISIBLE);
                        otherLayout.setVisibility(View.GONE);
                        otherCustomerType = "";
                    } else {
                        schoolSectionLayout.setVisibility(View.GONE);
                    }

                    if (typesName.equals("Other")) {
                        otherLayout.setVisibility(View.VISIBLE);
                        schoolSectionLayout.setVisibility(View.GONE);
                    } else {
                        otherLayout.setVisibility(View.GONE);
                        otherCustomerType = "";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getListOfTypes() {
        ProgressDialog spinnerDialogBox = ProgressDialog.show(this, "", "Fetching Types Please Wait...", true);
        GetDepartMent getDepartMent = new GetDepartMent(this);
        getDepartMent.FetchTypes(typesNameList, typesIdList, spinnerAdapter, spinnerDialogBox);
    }

    private void getBoardSpinnerList() {

        boardArrayList = new String[]{
                "Select Board"
        };
        boardNameList = new ArrayList<>(Arrays.asList(boardArrayList));
        spinnerAdapter = new AdapterForSpinner(this, R.layout.spinneritem, boardNameList);
        getListOfBoard();
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBoard.setAdapter(spinnerAdapter);
        spinnerBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    boardName = parent.getItemAtPosition(position).toString();
                    boardId = boardIdList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getListOfBoard() {
        ProgressDialog spinnerDialogBox = ProgressDialog.show(this, "", "Fetching Board Please Wait...", true);
        GetDepartMent getDepartMent = new GetDepartMent(this);
        getDepartMent.FetchBoard(boardNameList, boardIdList, spinnerAdapter, spinnerDialogBox);
    }

    private void getCategorySpinnerList() {

        categoryArrayList = new String[]{
                "Select Category"
        };
        categoryNameList = new ArrayList<>(Arrays.asList(categoryArrayList));
        spinnerAdapter = new AdapterForSpinner(this, R.layout.spinneritem, categoryNameList);
        getListOfCategory();
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    categoryName = parent.getItemAtPosition(position).toString();
                    categoryId = categoryIdList.get(position);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getListOfCategory() {
        ProgressDialog spinnerDialogBox = ProgressDialog.show(this, "", "Fetching Category Please Wait...", true);
        GetDepartMent getDepartMent = new GetDepartMent(this);
        getDepartMent.FetchCategory(categoryNameList, categoryIdList, spinnerAdapter, spinnerDialogBox);
    }

    private void getSalesOfMonthSpinnerList() {

        sampleOfMonthArrayList = new String[]{
                "Select Sample Of Month"
        };
        sampleOfMonthNameList = new ArrayList<>(Arrays.asList(sampleOfMonthArrayList));
        spinnerAdapter = new AdapterForSpinner(this, R.layout.spinneritem, sampleOfMonthNameList);
        getListOfSampleOfMonth();
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSampleOfMonth.setAdapter(spinnerAdapter);
        spinnerSampleOfMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    sampleOfMonthName = parent.getItemAtPosition(position).toString();
                    sampleOfMonthId = sampleOfMonthIdList.get(position);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getListOfSampleOfMonth() {
        ProgressDialog spinnerDialogBox = ProgressDialog.show(this, "", "Fetching Sample Of Month Please Wait...", true);
        GetDepartMent getDepartMent = new GetDepartMent(this);
        getDepartMent.FetchsampleOfMonth(sampleOfMonthNameList, sampleOfMonthIdList, spinnerAdapter, spinnerDialogBox);
    }

    private void getOrderOfMonthSpinnerList() {


        orderOfMonthArrayList = new String[]{
                "Select Order Of Month"
        };
        orderOfMonthNameList = new ArrayList<>(Arrays.asList(orderOfMonthArrayList));
        spinnerAdapter = new AdapterForSpinner(this, R.layout.spinneritem, orderOfMonthNameList);
        getListOfOrderOfMonth();
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrderOfMonth.setAdapter(spinnerAdapter);
        spinnerOrderOfMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    orderOfMonthName = parent.getItemAtPosition(position).toString();
                    orderOfMonthId = orderOfMonthIdList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getListOfOrderOfMonth() {
        ProgressDialog spinnerDialogBox = ProgressDialog.show(this, "", "Fetching Order Of Month Please Wait...", true);
        GetDepartMent getDepartMent = new GetDepartMent(this);
        getDepartMent.FetchOrderOfMonth(orderOfMonthNameList, orderOfMonthIdList, spinnerAdapter, spinnerDialogBox);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//        if (chkIsNextVisit.isChecked()) {
        if (isChecked) {
            isNextVisit = "Yes";
            layoutReminderDateRemark.setVisibility(View.VISIBLE);

        } else {
            isNextVisit = "No";
            layoutReminderDateRemark.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnNext) {

            SessionManager sessionManager = new SessionManager(this);
            HashMap<String, String> getAttId = sessionManager.getSchoolCheckInId();
            schoolCheckInId = getAttId.get(SessionManager.KEY_SCHOOL_CHECKINID);

//            schoolCheckInId = "1";

            contactPerson = txtContactPerson.getText().toString();
            email = txtEmail.getText().toString();
            contactNo = txtContactNo.getText().toString();
            strengthOfClass = txtStrengthOfClass.getText().toString();
            comment = txtComment.getText().toString();
            remark = txtRemark.getText().toString();
            selectedReminderDate = txtReminderDate.getText().toString();
            otherCustomerType = txtOther.getText().toString();

            if (chkPrePrimary.isChecked() == true || chkPrimary.isChecked() == true || chkSecondary.isChecked() == true) {

                StringBuilder groups = new StringBuilder();
                for (String value : schoolSectionList) {
                    groups.append(value + ",");
                }
                schoolSection = groups.toString();
            }

            if (typesName.equals("School") && schoolSection.equals("")) {
                Toast.makeText(this, "Please Select School Section.", Toast.LENGTH_SHORT).show();
            } else if (typesName.equals("Other") && otherCustomerType.equals("")) {
                Toast.makeText(this, "Please Enter Other Customer Details.", Toast.LENGTH_SHORT).show();
            } else if (typesName.equals("") || typesName == null || typesName.isEmpty()) {
                Toast.makeText(this, "Please Select Customer Type.", Toast.LENGTH_LONG).show();
            } else if (contactPerson.equals("")) {
                Toast.makeText(this, "Please Enter Contact Person.", Toast.LENGTH_SHORT).show();
            } else if (email.equals("")) {
                Toast.makeText(this, "Please Enter Email Id.", Toast.LENGTH_SHORT).show();
            } else if (contactNo.equals("")) {
                Toast.makeText(this, "Please Enter Contact No.", Toast.LENGTH_SHORT).show();
            } else if (boardName.equals("") || boardName == null || boardName.isEmpty()) {
                Toast.makeText(this, "Please Select Board.", Toast.LENGTH_LONG).show();
            } else if (categoryName.equals("") || categoryName == null || categoryName.isEmpty()) {
                Toast.makeText(this, "Please Select Category.", Toast.LENGTH_LONG).show();
            } else if (seriesPrefrence.equals("")) {
                Toast.makeText(this, "Please Select Series Preference.", Toast.LENGTH_SHORT).show();
            } else if (storyBook.equals("")) {
                Toast.makeText(this, "Please Select Story Book.", Toast.LENGTH_SHORT).show();
            } else if (strengthOfClass.equals("")) {
                Toast.makeText(this, "Please Enter Class Strength.", Toast.LENGTH_SHORT).show();
            } else if (sampleOfMonthName.equals("") || sampleOfMonthName == null || sampleOfMonthName.isEmpty()) {
                Toast.makeText(this, "Please Select Sample Month.", Toast.LENGTH_LONG).show();
            } else if (orderOfMonthName.equals("") || orderOfMonthName == null || orderOfMonthName.isEmpty()) {
                Toast.makeText(this, "Please Select Order Month.", Toast.LENGTH_LONG).show();
            } else if (isIntrested.equals("")) {
                Toast.makeText(this, "Please Select Response.", Toast.LENGTH_SHORT).show();
            } else if (statusOfCallName.equals("") || statusOfCallName == null || statusOfCallName.isEmpty()) {
                Toast.makeText(this, "Please Select Status Of Call.", Toast.LENGTH_LONG).show();
            } else {
                imFrom = "SubmitBtn";
                getCurrentLocation();
//                Intent in = new Intent(this, Reason_For_Visit.class);
//                startActivity(in);
            }


        } else if (v.getId() == R.id.txtReminderDate) {
            DatePickerDialog mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                    selectedReminderDate = String.valueOf(selectedyear) + "-" + String.valueOf(selectedmonth + 1) + "-" + String.valueOf(selectedday);
                    txtReminderDate.setText(selectedReminderDate);
                }
            }, year, month, day);
            mDatePicker.show();
        }
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

                                SchoolCheckInWebService task = new SchoolCheckInWebService();
                                task.execute();
                            } else if (imFrom.equals("SubmitBtn")) {
                                progressDialog2 = new ProgressDialog(School_Details.this);
                                progressDialog2.setMessage("Please Wait..");
                                progressDialog2.show();
                                progressDialog2.setCancelable(false);
                                progressDialog2.setCanceledOnTouchOutside(false);

                                SchoolCheckOutWebService task = new SchoolCheckOutWebService();
                                task.execute();
                            }
                        }
                    }
                });
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
//            case MY_PERMISSIONS_REQUEST_IMAGE: {
//                image.getRequestPermissionsResult(requestCode, permissions, grantResults);
//                return;
//            }
        }
    }

    private void ShowConfirmationDialog(final String dialogFor, String msg, String title) {

        if (dialogFor.equals("APIError")) {
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

                        if (dialogFor.equals("SubmitSuccessFull")) {
                            Intent it = new Intent(School_Details.this, School_List.class);
                            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(it);
                        } else if (dialogFor.equals("NotPunching")) {
                            Intent it = new Intent(School_Details.this, MainActivity.class);
                            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(it);
                        }
                    }
                })
                .show();
    }

    public class SchoolCheckInWebService extends AsyncTask<String, Void, Void> {

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

                dialogFor = "PunchInSuccessFull";
                msg = "You Have Checked In Successfully.";
                ShowConfirmationDialog(dialogFor, msg, title);

            } else if (responseResultCheckIn.equals("No Network Found")) {
                progressDialog.dismiss();

                title = "Response";
                dialogFor = "APIError";
                msg = "Unable To Checked In. Please Try Again Later.";
                ShowConfirmationDialog(dialogFor, msg, title);

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

    public class SchoolCheckOutWebService extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            method = "SchoolCheckinDetails";
//            responseResultCheckIn = WebService.SchoolCheckOut(userId, schoolId, schoolCheckInId, typesName, response, contactPerson, email, contactNo, boardName, categoryName, strengthOfClass, sampleOfMonthName, orderOfMonthName, isIntrested, statusOfCallName, comment, isNextVisit, selectedReminderDate, remark, String.valueOf(latitude), String.valueOf(longitude), method);
            responseResultCheckIn = WebService.SchoolCheckOut2(userId, schoolId, schoolCheckInId, typesName, schoolSection, otherCustomerType, contactPerson, email, contactNo, boardName, categoryName, seriesPrefrence, storyBook, strengthOfClass, sampleOfMonthName, orderOfMonthName, isIntrested, statusOfCallName, comment, isNextVisit, selectedReminderDate, remark, String.valueOf(latitude), String.valueOf(longitude), method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {

            if (responseResultCheckIn.equals("No Network Found")) {
                progressDialog2.dismiss();
                title = "Response";
                dialogFor = "APIError";
                msg = "Unable To Submit. Please Try Again Later.";
                ShowConfirmationDialog(dialogFor, msg, title);
            }
//            else {
//                progressDialog2.dismiss();
//                dialogFor = "SubmitSuccessFull";
//                msg = "Submitted Successfully.";
//                ShowConfirmationDialog(dialogFor, msg, title);
//            }
            else {
                String schoolDetailsId = "", schoolChekinId = "";
                progressDialog2.dismiss();
                sessionManager = new SessionManager(School_Details.this);
                try {

                    JSONArray jArr = new JSONArray(responseResultCheckIn);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        schoolDetailsId = obj.getString("SchoolCheckInDetailCopyID");
                        schoolChekinId = obj.getString("SchoolCheckinId");

                    }
                    sessionManager.SetSchoolDetailsId(schoolDetailsId, schoolChekinId);
                    Intent gotoReasonVisit = new Intent(School_Details.this, Reason_For_Visit.class);
                    startActivity(gotoReasonVisit);

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


}
