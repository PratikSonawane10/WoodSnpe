package com.mobitechs.woodsnipe;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitechs.woodsnipe.adapter.AdapterForSpinner;
import com.mobitechs.woodsnipe.adapter.Multiple_Images_Adapter;
import com.mobitechs.woodsnipe.connectivity.GetDepartMent;
import com.mobitechs.woodsnipe.connectivity.Submit_Reason_For_Visit;
import com.mobitechs.woodsnipe.imageSelect.Image;
import com.mobitechs.woodsnipe.sessionManager.SessionManager;
import com.mobitechs.woodsnipe.webService.WebService;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;
import com.sangcomz.fishbun.define.Define;

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

public class Reason_For_Visit extends BaseActivity implements View.OnClickListener {

    public String method;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    int noOfImage = 1;
    private StringBuilder date;

    Spinner spinnerReasonForVisit;
    String reasonForVisitName, reasonForVisitId;
    private String[] resonForVisitArrayList;
    private List<String> reasonForVisitIdList = new ArrayList<String>();
    private List<String> reasonForVisitNameList = new ArrayList<String>();

    private AdapterForSpinner spinnerAdapter;

    CheckBox chkTinySteps, chkLittleSteps, chkComputer, chkGrammar, chkCursive1, chkCursive2, chkCursive3, chkNoteBook, chkDrawingBook, chkScrapBook, chkGraphBook, chkOther;
    EditText txtTinyStepsQty, txtLittleStepsQty, txtComputerQty,txtGrammarQty, txtCursive1, txtCursive2, txtCursive3, txtNoteBookQty, txtDrawingBookQty, txtScrapBookQty, txtGraphBookQty, txtSampleOther, txtSampleOtherQty, txtSampleContactPerson, txtSampleContactNo, txtSampleEmailId;
    String tsq,lsq,comq,grmq,c1q,c2q,c3q,nbq,dbq,sbq,gbq,sampleOther,sampleOtherq,sampleContactPerson,sampleContactNo,sampleEmailId;

    String userId, schoolId = "", schoolName = "", schoolDetailsId,schoolChekinId;

    FloatingActionButton fabSubmit;
    String otherFor;
    Drawable icon;

    String otherReasonForVisit;

    //payment dialog details
    LinearLayout paymentImageLayout;
    TextView txtPaymentDate, lblPaymentAddImage;
    RadioGroup rdgPayment;
    RadioButton radioCheque, radioCash, radioNEFT;
    EditText txtPaymentAmount;
    ImageView imgPayment;
    String paymentMode = "", paymentDate ="", paymentAmount="";
    FloatingActionButton fabPaymentSubmit;

    //other dialog details
    EditText txtOther;
    Button btnOtherSubmit;

    //multipleImage
    Button btnSelectImages;
    FloatingActionButton btnSubmitImages;
    RecyclerView imageListRecyclerView;
    List<File> imgFileList = new ArrayList<File>();
    private String timeStamp;
    private File storageDir;
    private GridLayoutManager gridLayoutManager;
    private Multiple_Images_Adapter multipleImageAdapter;

    LinearLayout sampleLayout, paymentLayout, orderReturnsLayout, otherLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reason_for_visit);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> getAttId = sessionManager.GetSchoolDetailsId();
        schoolDetailsId = getAttId.get(SessionManager.KEY_SCHOOL_DETAILS_ID);
        schoolChekinId = getAttId.get(SessionManager.KEY_SCHOOL_CHECKINID);

        HashMap<String, String> typeOfUser = sessionManager.getUserDetails();
        userId = typeOfUser.get(SessionManager.KEY_USERID);

        icon = getResources().getDrawable(R.drawable.ic_watch_orange_24dp);


        Intent intent = getIntent();
        if (null != intent) {
            schoolId = intent.getStringExtra("schoolId");
            schoolName = intent.getStringExtra("schoolName");
        }

        spinnerReasonForVisit = (Spinner) findViewById(R.id.spinnerReasonForVisit);

        sampleLayout = (LinearLayout) findViewById(R.id.sampleLayout);
        paymentLayout = (LinearLayout) findViewById(R.id.paymentLayout);
        orderReturnsLayout = (LinearLayout) findViewById(R.id.orderReturnsLayout);
        otherLayout = (LinearLayout) findViewById(R.id.otherLayout);

        chkTinySteps = (CheckBox) findViewById(R.id.chkTinySteps);
        chkLittleSteps = (CheckBox) findViewById(R.id.chkLittleSteps);
        chkComputer = (CheckBox) findViewById(R.id.chkComputer);
        chkGrammar = (CheckBox) findViewById(R.id.chkGrammar);
        chkCursive1 = (CheckBox) findViewById(R.id.chkCursive1);
        chkCursive2 = (CheckBox) findViewById(R.id.chkCursive2);
        chkCursive3 = (CheckBox) findViewById(R.id.chkCursive3);
        chkNoteBook = (CheckBox) findViewById(R.id.chkNoteBook);
        chkDrawingBook = (CheckBox) findViewById(R.id.chkDrawingBook);
        chkScrapBook = (CheckBox) findViewById(R.id.chkScrapBook);
        chkGraphBook = (CheckBox) findViewById(R.id.chkGraphBook);
        chkOther = (CheckBox) findViewById(R.id.chkOther);

        txtTinyStepsQty = (EditText) findViewById(R.id.txtTinyStepsQty);
        txtLittleStepsQty = (EditText) findViewById(R.id.txtLittleStepsQty);
        txtComputerQty = (EditText) findViewById(R.id.txtComputerQty);
        txtGrammarQty = (EditText) findViewById(R.id.txtGrammarQty);
        txtCursive1 = (EditText) findViewById(R.id.txtCursive1);
        txtCursive2 = (EditText) findViewById(R.id.txtCursive2);
        txtCursive3 = (EditText) findViewById(R.id.txtCursive3);
        txtNoteBookQty = (EditText) findViewById(R.id.txtNoteBookQty);
        txtDrawingBookQty = (EditText) findViewById(R.id.txtDrawingBookQty);
        txtScrapBookQty = (EditText) findViewById(R.id.txtScrapBookQty);
        txtGraphBookQty = (EditText) findViewById(R.id.txtGraphBookQty);
        txtSampleOther = (EditText) findViewById(R.id.txtSampleOther);
        txtSampleOtherQty = (EditText) findViewById(R.id.txtSampleOtherQty);
        txtSampleContactPerson = (EditText) findViewById(R.id.txtSampleContactPerson);
        txtSampleContactNo = (EditText) findViewById(R.id.txtSampleContactNo);
        txtSampleEmailId = (EditText) findViewById(R.id.txtSampleEmailId);

        txtPaymentDate = (TextView) findViewById(R.id.txtPaymentDate);
        lblPaymentAddImage = (TextView) findViewById(R.id.lblPaymentAddImage);
        rdgPayment = (RadioGroup) findViewById(R.id.rdgPayment);
        radioCheque = (RadioButton) findViewById(R.id.radioCheque);
        radioCash = (RadioButton) findViewById(R.id.radioCash);
        radioNEFT = (RadioButton) findViewById(R.id.radioNEFT);
        txtPaymentAmount = (EditText) findViewById(R.id.txtPaymentAmount);
        imgPayment = (ImageView) findViewById(R.id.imgPayment);
        fabPaymentSubmit = (FloatingActionButton) findViewById(R.id.fabPaymentSubmit);
        paymentImageLayout = (LinearLayout) findViewById(R.id.paymentImageLayout);

        btnSelectImages = (Button) findViewById(R.id.btnSelectImages);
        btnSubmitImages = (FloatingActionButton) findViewById(R.id.btnSubmitImages);
        imageListRecyclerView = (RecyclerView) findViewById(R.id.imageListRecyclerView);

        btnSubmitImages.setVisibility(View.GONE);
        btnSelectImages.setOnClickListener(this);
        btnSubmitImages.setOnClickListener(this);

        txtOther = (EditText) findViewById(R.id.txtOther);
        btnOtherSubmit = (Button) findViewById(R.id.btnOtherSubmit);
        btnOtherSubmit.setOnClickListener(this);

        fabSubmit = (FloatingActionButton) findViewById(R.id.fabSubmit);

        fabSubmit.setOnClickListener(this);
        txtPaymentDate.setOnClickListener(this);
        paymentImageLayout.setOnClickListener(this);
        fabPaymentSubmit.setOnClickListener(this);

        sampleLayout.setVisibility(View.GONE);
        paymentLayout.setVisibility(View.GONE);
        orderReturnsLayout.setVisibility(View.GONE);
        otherLayout.setVisibility(View.GONE);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        String currMonth = String.valueOf(month + 1);
        paymentAmount = day + "-" + currMonth + "-" + year;

        rdgPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                String radioName = String.valueOf(checkedRadioButton.getText());

                if (radioName.equals("Cheque")) {
                    paymentMode = "Cheque";
                } else if (radioName.equals("Cash")) {
                    paymentMode = "Cash";
                } else if (radioName.equals("NEFT")) {
                    paymentMode = "NEFT";
                }
            }
        });

        getReasonForVisitSpinnerList();

    }

    private void getReasonForVisitSpinnerList() {
        resonForVisitArrayList = new String[]{
                "Select Reason"
        };
        reasonForVisitNameList = new ArrayList<>(Arrays.asList(resonForVisitArrayList));
        spinnerAdapter = new AdapterForSpinner(this, R.layout.spinneritem, reasonForVisitNameList);
        getListOfReasonFoVisit();
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReasonForVisit.setAdapter(spinnerAdapter);
        spinnerReasonForVisit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    reasonForVisitName = parent.getItemAtPosition(position).toString();
                    reasonForVisitId = reasonForVisitIdList.get(position);

                    if (reasonForVisitName.equals("Sample")) {
                        sampleLayout.setVisibility(View.VISIBLE);
                        paymentLayout.setVisibility(View.GONE);
                        orderReturnsLayout.setVisibility(View.GONE);
                        otherLayout.setVisibility(View.GONE);
                    } else if (reasonForVisitName.equals("Payment")) {
                        sampleLayout.setVisibility(View.GONE);
                        paymentLayout.setVisibility(View.VISIBLE);
                        orderReturnsLayout.setVisibility(View.GONE);
                        otherLayout.setVisibility(View.GONE);
                    } else if (reasonForVisitName.equals("Order") || reasonForVisitName.equals("Returns")) {
                        sampleLayout.setVisibility(View.GONE);
                        paymentLayout.setVisibility(View.GONE);
                        orderReturnsLayout.setVisibility(View.VISIBLE);
                        otherLayout.setVisibility(View.GONE);
                    } else if (reasonForVisitName.equals("Other")) {
                        otherFor = "ReasonForVisitOther";
                        sampleLayout.setVisibility(View.GONE);
                        paymentLayout.setVisibility(View.GONE);
                        orderReturnsLayout.setVisibility(View.GONE);
                        otherLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getListOfReasonFoVisit() {
        ProgressDialog spinnerDialogBox = ProgressDialog.show(this, "", "Fetching Reason For Visit Please Wait...", true);
        GetDepartMent getDepartMent = new GetDepartMent(this);
        getDepartMent.FetchReasonForVisit(reasonForVisitNameList, reasonForVisitIdList, spinnerAdapter, spinnerDialogBox);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.txtPaymentDate) {
            DatePickerDialog mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    paymentDate = String.valueOf(selectedyear) + "-" + String.valueOf(selectedmonth + 1) + "-" + String.valueOf(selectedday);
                    txtPaymentDate.setText(paymentDate);
                }
            }, year, month, day);
            mDatePicker.show();
        } else if (v.getId() == R.id.paymentImageLayout) {
            noOfImage = 1;
            ImageSelector(noOfImage);
        }
        else if (v.getId() == R.id.btnSelectImages) {
            noOfImage = 5;
            ImageSelector(noOfImage);
        }
        else if (v.getId() == R.id.fabSubmit) {

            tsq = txtTinyStepsQty.getText().toString();
            lsq = txtLittleStepsQty.getText().toString();
            comq = txtComputerQty.getText().toString();
            grmq = txtGrammarQty.getText().toString();
            c1q = txtCursive1.getText().toString();
            c2q = txtCursive2.getText().toString();
            c3q = txtCursive3.getText().toString();
            nbq = txtNoteBookQty.getText().toString();
            dbq = txtDrawingBookQty.getText().toString();
            sbq = txtScrapBookQty.getText().toString();
            gbq = txtGraphBookQty.getText().toString();

            sampleOther = txtSampleOther.getText().toString();
            sampleOtherq = txtSampleOtherQty.getText().toString();

            sampleContactPerson = txtSampleContactPerson.getText().toString();
            sampleContactNo = txtSampleContactNo.getText().toString();
            sampleEmailId = txtSampleEmailId.getText().toString();

            if(sampleContactPerson.equals("")){
                Toast.makeText(this, "Please enter contact person.", Toast.LENGTH_SHORT).show();
            } else if(sampleContactNo.equals("")){
                Toast.makeText(this, "Please enter contact no.", Toast.LENGTH_SHORT).show();
            }else if(sampleEmailId.equals("")){
                Toast.makeText(this, "Please enter email.", Toast.LENGTH_SHORT).show();
            }
            else{
                ProgressDialog pd = ProgressDialog.show(this, "", "Please Wait...", true);
                Submit_Reason_For_Visit getDepartMent = new Submit_Reason_For_Visit(this);
                getDepartMent.SubmitReasonForSample(schoolDetailsId, userId, reasonForVisitName,tsq,lsq,comq,grmq,c1q,c2q,c3q,nbq,dbq,sbq,gbq,sampleOther,sampleOtherq,sampleContactPerson,sampleContactNo,sampleEmailId,pd);

            }

        }

        ////////////////////////////////////////////////////////////////////////
        else if (v.getId() == R.id.fabPaymentSubmit) {

            paymentAmount = txtPaymentAmount.getText().toString();
            if (paymentMode.equals("")) {
                Toast.makeText(this, "Please select payment mode.", Toast.LENGTH_SHORT).show();
            } 
            else if (paymentAmount.equals("")) {
                Toast.makeText(this, "Please enter amount.", Toast.LENGTH_SHORT).show();
            }
            else {
                File imgFile = imgFileList.get(0);
                ProgressDialog pd = ProgressDialog.show(this, "", "Please Wait...", true);
                Submit_Reason_For_Visit getDepartMent = new Submit_Reason_For_Visit(this);
                getDepartMent.SubmitReasonForPayment(schoolDetailsId, userId, reasonForVisitName,paymentDate, paymentMode,paymentAmount,imgFile,pd);
            }
        }


        ////////////////////////////////////////////////////////////////////////
        else if (v.getId() == R.id.btnOtherSubmit) {
            String other = txtOther.getText().toString();
            if (other.equals("")) {
                Toast.makeText(this, "Please Enter Other Details.", Toast.LENGTH_SHORT).show();
            } else {
                otherReasonForVisit = other;
                ProgressDialog pd = ProgressDialog.show(this, "", "Please Wait...", true);
                Submit_Reason_For_Visit getDepartMent = new Submit_Reason_For_Visit(this);
                getDepartMent.SubmitReasonForOther(schoolDetailsId, userId, reasonForVisitName, otherReasonForVisit, pd);
            }
        }

    }

    private void ImageSelector(int noOfImage) {
        FishBun.with(this)
                .setImageAdapter(new GlideAdapter())
                .setAllViewTitle("All")
                .setMaxCount(noOfImage)
                .setActionBarColor(Color.parseColor("#673AB7"), Color.parseColor("#512DA8"), false)
                .setActionBarTitle("Image Library  ")
                .textOnImagesSelectionLimitReached("You are not allowed to select more images!")
                .textOnNothingSelected("You did not select any images")
                .startAlbum();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == Define.ALBUM_REQUEST_CODE && resultCode == RESULT_OK && null != data) {
                imgFileList.clear();
                ArrayList<Parcelable> path = data.getParcelableArrayListExtra(Define.INTENT_PATH);
                for (int i = 0; i < path.size(); i++) {
                    Uri uri = (Uri) path.get(i);
                    String filePath = getRealPathFromURI(uri);
                    uri = Uri.fromFile(new File(filePath));
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    storageDir = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DCIM);
                    File newFile = new File(storageDir + "/" + timeStamp + i + ".png");
                    try {
                        OutputStream os = new FileOutputStream(newFile);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                        os.flush();
                        os.close();
                    } catch (Exception e) {
                        Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                    }
                    imageCompress(newFile);
                }

                if (noOfImage == 1) {
                    lblPaymentAddImage.setText("Click here to change image");
                    File imgFile = imgFileList.get(0);
                    imgPayment.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
                } else {
                    btnSubmitImages.setVisibility(View.VISIBLE);
                    imageListRecyclerView.setHasFixedSize(true);
                    gridLayoutManager = new GridLayoutManager(this, 3);
                    imageListRecyclerView.setLayoutManager(gridLayoutManager);

                    imageListRecyclerView.smoothScrollToPosition(0);
                    multipleImageAdapter = new Multiple_Images_Adapter(imgFileList);
                    imageListRecyclerView.setAdapter(multipleImageAdapter);
                }
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    public void imageCompress(File sourceFile) {
        Bitmap tempBitmap = null;
        try {
            tempBitmap = new Compressor(this)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .compressToBitmap(sourceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            OutputStream os = new FileOutputStream(sourceFile);
            tempBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        imgFileList.add(sourceFile);
    }

}
