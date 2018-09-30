package com.mobitechs.woodsnipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.mobitechs.woodsnipe.sessionManager.SessionManager;
import com.mobitechs.woodsnipe.webService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class School_Add extends BaseActivity implements View.OnClickListener {

    EditText txtSchoolName,txtSchoolContactNo,txtSchoolAddress,txtSchoolEmail;
    String schoolName,schoolContactNo,schoolAddress,schoolEmail;
    Button btnSubmit;
    ProgressBar progressBar;
    String userId;
    String method,responseResultPunchIn,title,dialogFor,msg;
    Drawable icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_add);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> typeOfUser = sessionManager.getUserDetails();
        userId = typeOfUser.get(SessionManager.KEY_USERID);

        txtSchoolName = (EditText) findViewById(R.id.txtSchoolName);
        txtSchoolContactNo = (EditText) findViewById(R.id.txtSchoolContactNo);
        txtSchoolEmail = (EditText) findViewById(R.id.txtSchoolEmail);
        txtSchoolAddress = (EditText) findViewById(R.id.txtSchoolAddress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        progressBar.setVisibility(View.GONE);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmit) {
            schoolName = txtSchoolName.getText().toString();
            schoolContactNo = txtSchoolContactNo.getText().toString();
            schoolEmail = txtSchoolEmail.getText().toString();
            schoolAddress = txtSchoolAddress.getText().toString();

            if (schoolName.equals("")) {
                Toast.makeText(this, "Please Enter School Name.", Toast.LENGTH_SHORT).show();
            }
            else if (schoolContactNo.equals("")) {
                Toast.makeText(this, "Please Enter School Contact No.", Toast.LENGTH_SHORT).show();
            }
            else if (schoolEmail.equals("")) {
                Toast.makeText(this, "Please Enter School Email .", Toast.LENGTH_SHORT).show();
            }
            else if (schoolAddress.equals("")) {
                Toast.makeText(this, "Please Enter School Address.", Toast.LENGTH_SHORT).show();
            }
            else {
                progressBar.setVisibility(View.VISIBLE);

                InsertSchool task = new InsertSchool();
                task.execute();


            }
        }
    }

    public class InsertSchool extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            method = "InsertSchool";
            responseResultPunchIn = WebService.InsertSchool(userId, schoolName,schoolContactNo,schoolEmail,schoolAddress, method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            progressBar.setVisibility(View.GONE);
            if (responseResultPunchIn.equals("No Network Found")) {
                title="Result";
                dialogFor ="APIError";
                msg = "Unable To Add School. Please Try Again Later.";
                ShowConfirmationDialog(dialogFor,msg,title);
            }
            else {
                try {
//                    JSONObject obj = new JSONObject(responseResultLocation);

                    JSONArray jArr = new JSONArray(responseResultPunchIn);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
//                        attendanceId = obj.getString("AttendanceID");
//                        punchInTime = obj.getString("PunchIn");
//                        punchOutTime = obj.getString("PunchOut");
                        String punchInMsg = obj.getString("msg");

                        if (punchInMsg.equals("School Inserted Successfully")) {

                            title="Result";
                            dialogFor ="School Add";
                            msg = "School Added Successfully.";
                            ShowConfirmationDialog(dialogFor,msg,title);
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
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

                        if(dialogFor.equals("School Add")){

                            Intent gotoList = new Intent(School_Add.this,School_List.class);
                            startActivity(gotoList);
                        }

                    }
                })
                .show();

    }


}