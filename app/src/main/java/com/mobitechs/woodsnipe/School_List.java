package com.mobitechs.woodsnipe;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.mobitechs.woodsnipe.adapter.School_List_Adapter;
import com.mobitechs.woodsnipe.model.School_Items;
import com.mobitechs.woodsnipe.sessionManager.SessionManager;
import com.mobitechs.woodsnipe.webService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class School_List extends BaseActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    School_List_Adapter adapter;
    LinearLayoutManager linearLayoutManager;
    JSONArray listArray = null;

    public List<School_Items> listItems = new ArrayList<School_Items>();
    EditText txtSearch;
    String searchText,userId;
    String webMethName,ResponseResult;
    ProgressDialog progressDialog = null;

    FloatingActionButton btnFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_list);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> typeOfUser = sessionManager.getUserDetails();
        userId = typeOfUser.get(SessionManager.KEY_USERID);

        txtSearch = (EditText) findViewById(R.id.txtSearch);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        btnFab = (FloatingActionButton) findViewById(R.id.btnFab);
        btnFab.setOnClickListener(this);

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.smoothScrollToPosition(0);
        adapter = new School_List_Adapter(this, listItems, recyclerView);
        recyclerView.setAdapter(adapter);

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                searchText = String.valueOf(s);
//                Toast.makeText(v.getContext(), ""+searchText, Toast.LENGTH_SHORT).show();
                adapter.getFilter().filter(searchText);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

        });

        getList();
    }

    private void getList() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.");
        progressDialog.show();

        AsyncCallWS task = new AsyncCallWS();
        task.execute();

    }

    public class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            webMethName = "GetSchoolList";
            ResponseResult = WebService.ShowSchoolList(userId, webMethName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (ResponseResult.equals("School Details Not Found.")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(School_List.this);
                builder.setTitle("Result");
                builder.setMessage("School List Not Available.");
                AlertDialog alert1 = builder.create();
                alert1.show();

            } else if (ResponseResult.equals("No Network Found")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(School_List.this);
                builder.setTitle("Result");
                builder.setMessage("There is Some Network Issues Please Try Again Later.");
                AlertDialog alert1 = builder.create();
                alert1.show();

            } else {
                progressDialog.dismiss();
                listItems.clear();
                try {
                    JSONArray jsonArray = new JSONArray(ResponseResult);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            School_Items item = new School_Items();
                            item.setSchoolId(obj.getString("SchoolID"));
                            item.setSchoolName(obj.getString("SchoolName"));
                            item.setSchoolAddress(obj.getString("Address"));
                            item.setSchoolContactNo(obj.getString("Contact"));
                            item.setSchoolEmail(obj.getString("EmailID"));
                            item.setCheckinDate(obj.getString("CheckinDate"));
                            item.setCheckoutDate(obj.getString("CheckoutDate"));
                            listItems.add(item);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        if(v.getId() == R.id.btnFab){
            Intent gotoAddPage = new Intent(this,School_Add.class);
            gotoAddPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(gotoAddPage);
        }
    }
}
