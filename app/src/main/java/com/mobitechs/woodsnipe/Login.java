package com.mobitechs.woodsnipe;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobitechs.woodsnipe.internetConnectivity.NetworkChangeReceiver;
import com.mobitechs.woodsnipe.sessionManager.SessionManager;
import com.mobitechs.woodsnipe.webService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private long TIME = 5000;
    private EditText editTextUserId;
    private EditText editTextPassword;
    private Button btnSignIn;
    private ProgressDialog progressDialog;

    private String userId;
    private String userPassword;
    private String method;
    public String loginResponseResult;

    SessionManager sessionManager;
    public String saveUserId;
    public String saveName;
//    public String saveEmail;
//    public String saaveMobileNo;
    public String DeptType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextUserId = (EditText) findViewById(R.id.txtLoginUserId);
        editTextPassword = (EditText) findViewById(R.id.txtLoginpassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(this);

    }

    @Override
    public void onClick(final View v) {

        if (v.getId() == R.id.btnSignIn) {

            userId = editTextUserId.getText().toString();
            userPassword = editTextPassword.getText().toString();

            if (editTextUserId.getText().toString().isEmpty()) {
                Toast.makeText(Login.this, "Please Enter Your User Id.", Toast.LENGTH_LONG).show();
            }
            else if (editTextPassword.getText().toString().isEmpty()) {
                Toast.makeText(Login.this, "Please Enter Password.", Toast.LENGTH_LONG).show();
            }
//            else if (spinnerItemName == null || spinnerItemName.isEmpty()) {
//                Toast.makeText(this, "Please select Standard.", Toast.LENGTH_LONG).show();
//            }
            else {
                progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Logging In.");
                progressDialog.show();

//                Intent gotoformhome = new Intent(this, MainActivity.class);
//                startActivity(gotoformhome);
//                progressDialog.dismiss();

                LoginAsyncCallWS task = new LoginAsyncCallWS();
                task.execute();
            }
        }
    }

    public class LoginAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            method = "Login";
            loginResponseResult = WebService.CreateLogin(userId, userPassword, method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (loginResponseResult.equals("Invalid UserName or Password.")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Result");
                builder.setMessage(loginResponseResult);
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
            } else if (loginResponseResult.equals("No Network Found")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Result");
                builder.setMessage("Unable To Login. Please Try Again Later.");
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
                progressDialog.dismiss();
                sessionManager = new SessionManager(Login.this);
                try {
                    JSONArray jArr = new JSONArray(loginResponseResult);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        saveName = obj.getString("Name");
                        saveUserId = obj.getString("EmpDetailID");
                        //saveEmail = obj.getString("EmailId");
                        //saaveMobileNo = obj.getString("MobileNo");
                        DeptType = obj.getString("DesignationID");

                        sessionManager.createUserLoginSession(saveUserId, saveName,DeptType);
                        editTextUserId.setText("");
                        editTextPassword.setText("");
                        Intent gotoHome = new Intent(Login.this, MainActivity.class);
                        startActivity(gotoHome);
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        PackageManager pm = Login.this.getPackageManager();
        ComponentName component = new ComponentName(Login.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

    }

    @Override
    protected void onResume() {
        super.onResume();

        PackageManager pm = Login.this.getPackageManager();
        ComponentName component = new ComponentName(Login.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
