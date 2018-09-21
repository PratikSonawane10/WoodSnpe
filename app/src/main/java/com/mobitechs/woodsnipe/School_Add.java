package com.mobitechs.woodsnipe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class School_Add extends BaseActivity implements View.OnClickListener {

    EditText txtSchoolName,txtSchoolContactNo,txtSchoolAddress,txtSchoolEmail;
    String schoolName,schoolContactNo,customerId,imFrom,schoolAddress,schoolEmail;
    Button btnSubmit;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_add);

        txtSchoolName = (EditText) findViewById(R.id.txtSchoolName);
        txtSchoolContactNo = (EditText) findViewById(R.id.txtSchoolContactNo);
        txtSchoolAddress = (EditText) findViewById(R.id.txtSchoolAddress);
        txtSchoolEmail = (EditText) findViewById(R.id.txtSchoolEmail);
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
            schoolAddress = txtSchoolAddress.getText().toString();
            schoolEmail = txtSchoolEmail.getText().toString();

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



            }
        }
    }

}