package com.mobitechs.woodsnipe.connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.mobitechs.woodsnipe.Reason_For_Visit;
import com.mobitechs.woodsnipe.School_Details;
import com.mobitechs.woodsnipe.adapter.AdapterForSpinner;
import com.mobitechs.woodsnipe.webService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetDepartMent {

    private static Context context;
    private static AdapterForSpinner SpinnerAdapterReasonForVisit;
    private static AdapterForSpinner SpinnerAdapterStatus;
    private static AdapterForSpinner SpinnerAdapterTypes;
    private static AdapterForSpinner SpinnerAdapterBoard;
    private static AdapterForSpinner SpinnerAdapterCategory;
    private static AdapterForSpinner SpinnerAdapterSampleOfMonth;
    private static AdapterForSpinner SpinnerAdapterOrderOfMonth;

    private static List<String> allReasonForVisitNameList = new ArrayList<String>();
    private static List<String> allReasonForVisitIdList = new ArrayList<String>();
    private static ProgressDialog reasonForVisitSpinnerDialogBox;
    private static String reasonForVisitResponseResult;

    private static List<String> allStatusNameList = new ArrayList<String>();
    private static List<String> allStatusIdList = new ArrayList<String>();
    private static ProgressDialog statusSpinnerDialogBox;
    private static String statusResponseResult;

    private static List<String> typesNameList = new ArrayList<String>();
    private static List<String> typesIdList = new ArrayList<String>();
    private static ProgressDialog typesSpinnerDialogBox;
    private static String typesResponseResult;

    private static List<String> boardNameList = new ArrayList<String>();
    private static List<String> boardIdList = new ArrayList<String>();
    private static ProgressDialog boardSpinnerDialogBox;
    private static String boardResponseResult;

    private static List<String> categoryNameList = new ArrayList<String>();
    private static List<String> categoryIdList = new ArrayList<String>();
    private static ProgressDialog categorySpinnerDialogBox;
    private static String categoryResponseResult;

    private static List<String> sampleOfMonthNameList = new ArrayList<String>();
    private static List<String> sampleOfMonthIdList = new ArrayList<String>();
    private static ProgressDialog sampleOFMonthSpinnerDialogBox;
    private static String sampleOfMonthResponseResult;

    private static List<String> orderOfMonthNameList = new ArrayList<String>();
    private static List<String> orderOfMonthIdList = new ArrayList<String>();
    private static ProgressDialog orderOFMonthSpinnerDialogBox;
    private static String orderOfMonthResponseResult;


    private static String methodReasonForVisit = "", methodStatus, methodTypes, methodBoard, methodcaegory, methodSampleOfMonth, methodOrderOfMonth;

    public GetDepartMent(School_Details sd) {
        context = sd;
    }

    public GetDepartMent(Reason_For_Visit reason_for_visit) {
        context = reason_for_visit;
    }

    public void FetchallStatusOfC(List<String> spinnerItemNameList, List<String> spinnerItemIdList, AdapterForSpinner spinnerAdapter, ProgressDialog progressDialogBox) {
        allStatusNameList = spinnerItemNameList;
        allStatusIdList = spinnerItemIdList;
        SpinnerAdapterStatus = spinnerAdapter;
        statusSpinnerDialogBox = progressDialogBox;
        methodStatus = "Satus";
        AsyncCallStatusWS task = new AsyncCallStatusWS();
        task.execute();

    }

    public void FetchTypes(List<String> typesNameListS, List<String> typesIdListS, AdapterForSpinner spinnerAdapter, ProgressDialog spinnerDialogBox) {

        typesNameList = typesNameListS;
        typesIdList = typesIdListS;
        SpinnerAdapterTypes = spinnerAdapter;
        typesSpinnerDialogBox = spinnerDialogBox;
        methodTypes = "CustomerTypes";
        AsyncCallTypesWS task = new AsyncCallTypesWS();
        task.execute();
    }

    public void FetchBoard(List<String> boardNameLists, List<String> boardIdLists, AdapterForSpinner spinnerAdapter, ProgressDialog spinnerDialogBox) {

        boardNameList = boardNameLists;
        boardIdList = boardIdLists;
        SpinnerAdapterBoard = spinnerAdapter;
        boardSpinnerDialogBox = spinnerDialogBox;
        methodBoard = "Board";
        AsyncCallBoardWS task = new AsyncCallBoardWS();
        task.execute();
    }

    public void FetchCategory(List<String> categoryNameLists, List<String> categoryIdLists, AdapterForSpinner spinnerAdapter, ProgressDialog spinnerDialogBox) {

        categoryNameList = categoryNameLists;
        categoryIdList = categoryIdLists;
        SpinnerAdapterCategory = spinnerAdapter;
        categorySpinnerDialogBox = spinnerDialogBox;
        methodcaegory = "Category";
        AsyncCallcategoryWS task = new AsyncCallcategoryWS();
        task.execute();
    }

    public void FetchsampleOfMonth(List<String> sampleOfMonthNameLists, List<String> sampleOfMonthIdLists, AdapterForSpinner spinnerAdapter, ProgressDialog spinnerDialogBox) {

        sampleOfMonthNameList = sampleOfMonthNameLists;
        sampleOfMonthIdList = sampleOfMonthIdLists;
        SpinnerAdapterSampleOfMonth = spinnerAdapter;
        sampleOFMonthSpinnerDialogBox = spinnerDialogBox;
        methodSampleOfMonth = "Month";
        AsyncSampleOfMonthWS task = new AsyncSampleOfMonthWS();
        task.execute();
    }

    public void FetchOrderOfMonth(List<String> orderOfMonthNameLists, List<String> orderOfMonthIdLists, AdapterForSpinner spinnerAdapter, ProgressDialog spinnerDialogBox) {
        orderOfMonthNameList = orderOfMonthNameLists;
        orderOfMonthIdList = orderOfMonthIdLists;
        SpinnerAdapterOrderOfMonth = spinnerAdapter;
        orderOFMonthSpinnerDialogBox = spinnerDialogBox;
        methodOrderOfMonth = "Month";
        AsyncOrderOfMonthWS task = new AsyncOrderOfMonthWS();
        task.execute();
    }

    public class AsyncOrderOfMonthWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            orderOfMonthResponseResult = WebService.GetMonthList(methodOrderOfMonth);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (orderOfMonthResponseResult.equals("Invalid Department")) {
                orderOFMonthSpinnerDialogBox.dismiss();
            } else if (orderOfMonthResponseResult.equals("No Network Found")) {
                orderOFMonthSpinnerDialogBox.dismiss();
            } else {
                orderOFMonthSpinnerDialogBox.dismiss();
                orderOfMonthIdList.add("0");
                try {
                    JSONArray jArr = new JSONArray(orderOfMonthResponseResult);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        orderOfMonthNameList.add(obj.getString("Month"));
                        orderOfMonthIdList.add(obj.getString("MonthMasterID"));
                    }
                    SpinnerAdapterOrderOfMonth.notifyDataSetChanged();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public class AsyncSampleOfMonthWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            sampleOfMonthResponseResult = WebService.GetMonthList(methodOrderOfMonth);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (sampleOfMonthResponseResult.equals("Invalid Department")) {
                sampleOFMonthSpinnerDialogBox.dismiss();
            } else if (sampleOfMonthResponseResult.equals("No Network Found")) {
                sampleOFMonthSpinnerDialogBox.dismiss();
            } else {
                sampleOFMonthSpinnerDialogBox.dismiss();
                sampleOfMonthIdList.add("0");
                try {
                    JSONArray jArr = new JSONArray(sampleOfMonthResponseResult);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        sampleOfMonthNameList.add(obj.getString("Month"));
                        sampleOfMonthIdList.add(obj.getString("MonthMasterID"));
                    }
                    SpinnerAdapterSampleOfMonth.notifyDataSetChanged();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public class AsyncCallcategoryWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            categoryResponseResult = WebService.GetCategoryList(methodcaegory);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (categoryResponseResult.equals("Invalid Department")) {
                categorySpinnerDialogBox.dismiss();
            } else if (categoryResponseResult.equals("No Network Found")) {
                categorySpinnerDialogBox.dismiss();
            } else {
                categorySpinnerDialogBox.dismiss();
                categoryIdList.add("0");
                try {
                    JSONArray jArr = new JSONArray(categoryResponseResult);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        categoryNameList.add(obj.getString("Catgeory"));
                        categoryIdList.add(obj.getString("CategoryMasterID"));
                    }
                    SpinnerAdapterCategory.notifyDataSetChanged();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public class AsyncCallBoardWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            boardResponseResult = WebService.GetBoardList(methodBoard);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (boardResponseResult.equals("Invalid Department")) {
                boardSpinnerDialogBox.dismiss();
            } else if (boardResponseResult.equals("No Network Found")) {
                boardSpinnerDialogBox.dismiss();
            } else {
                boardSpinnerDialogBox.dismiss();
                boardIdList.add("0");
                try {
                    JSONArray jArr = new JSONArray(boardResponseResult);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        boardNameList.add(obj.getString("Board"));
                        boardIdList.add(obj.getString("BoardTypeID"));
                    }
                    SpinnerAdapterBoard.notifyDataSetChanged();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public class AsyncCallTypesWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            typesResponseResult = WebService.CustomerTypes(methodTypes);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (typesResponseResult.equals("Invalid Department")) {
                typesSpinnerDialogBox.dismiss();
            } else if (typesResponseResult.equals("No Network Found")) {
                typesSpinnerDialogBox.dismiss();
            } else {
                typesSpinnerDialogBox.dismiss();
                typesIdList.add("0");
                try {
                    JSONArray jArr = new JSONArray(typesResponseResult);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        typesNameList.add(obj.getString("Type"));
                        typesIdList.add(obj.getString("CustomerTypeID"));
                    }
                    SpinnerAdapterTypes.notifyDataSetChanged();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public class AsyncCallStatusWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            statusResponseResult = WebService.GetStatusOfCall(methodStatus);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (statusResponseResult.equals("Invalid Department")) {
                statusSpinnerDialogBox.dismiss();
            } else if (statusResponseResult.equals("No Network Found")) {
                statusSpinnerDialogBox.dismiss();
            } else {
                statusSpinnerDialogBox.dismiss();
                allStatusIdList.add("0");
                try {
                    JSONArray jArr = new JSONArray(statusResponseResult);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        allStatusNameList.add(obj.getString("Status"));
                        allStatusIdList.add(obj.getString("SatusMasterID"));
                    }
                    SpinnerAdapterStatus.notifyDataSetChanged();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void FetchReasonForVisit(List<String> reasonForVisitNameList, List<String> resonForVisitIdList, AdapterForSpinner spinnerAdapter, ProgressDialog spinnerDialogBox) {
        allReasonForVisitNameList = reasonForVisitNameList;
        allReasonForVisitIdList = resonForVisitIdList;
        SpinnerAdapterReasonForVisit = spinnerAdapter;
        reasonForVisitSpinnerDialogBox = spinnerDialogBox;
        methodReasonForVisit = "ResonForVisit";
        AsyncCallReasonForVisitWS task = new AsyncCallReasonForVisitWS();
        task.execute();
    }

    public class AsyncCallReasonForVisitWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            reasonForVisitResponseResult = WebService.GetReasonForVisit(methodReasonForVisit);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (reasonForVisitResponseResult.equals("Invalid ReasonForVisit")) {
                reasonForVisitSpinnerDialogBox.dismiss();
            } else if (reasonForVisitResponseResult.equals("No Network Found")) {
                reasonForVisitSpinnerDialogBox.dismiss();
            } else {
                reasonForVisitSpinnerDialogBox.dismiss();
                allReasonForVisitIdList.add("0");
                try {
                    JSONArray jArr = new JSONArray(reasonForVisitResponseResult);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        allReasonForVisitNameList.add(obj.getString("Visit"));
                        allReasonForVisitIdList.add(obj.getString("ResonForVisitID"));
                    }
                    SpinnerAdapterReasonForVisit.notifyDataSetChanged();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
