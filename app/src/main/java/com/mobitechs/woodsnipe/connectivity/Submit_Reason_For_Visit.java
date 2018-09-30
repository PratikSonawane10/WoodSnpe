package com.mobitechs.woodsnipe.connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mobitechs.woodsnipe.Reason_For_Visit;
import com.mobitechs.woodsnipe.webService.WebService;

import java.io.File;
import java.util.List;


public class Submit_Reason_For_Visit {
    Context context;
    String method,responseResult,schoolDetailsId,userId,reasonForVisitName;
    ProgressDialog progressDialog;

    String otherReasonForVisit;
    String paymentDate,paymentMode, paymentAmount;
    File paymentImgFile;

    String  TinyStepsQty,  LittleStepsQty,  ComputerQty, GrammarQty,  Cursive1,  Cursive2,  Cursive3,  NoteBookQty,  DrawingBookQty,  ScrapBookQty,  GraphBookQty,  SampleOther,  SampleOtherQty,  SampleContactPerson,  SampleContactNo,  SampleEmailId;



    public Submit_Reason_For_Visit(Reason_For_Visit reason_for_visit) {
        context = reason_for_visit;
    }

    public void SubmitReasonForOther(String schoolDetailsIdS, String userIdS, String reasonForVisitNameS, String otherReasonForVisitS, ProgressDialog pd) {

        schoolDetailsId = schoolDetailsIdS;
        userId = userIdS;
        reasonForVisitName = reasonForVisitNameS;
        otherReasonForVisit = otherReasonForVisitS;
        method = "SubmitReasonForVisitOther";
        progressDialog = pd;
        AsyncCallReasonForVisitOtherWS task = new AsyncCallReasonForVisitOtherWS();
        task.execute();
    }


    public class AsyncCallReasonForVisitOtherWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            responseResult = WebService.SubmitReasonForVisitOther(schoolDetailsId,userId,reasonForVisitName,otherReasonForVisit,method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (responseResult.equals("Invalid ReasonForVisit")) {
                progressDialog.dismiss();
            }
            else if (responseResult.equals("No Network Found")) {
                progressDialog.dismiss();
            }
            else {

            }
        }
    }


    public void SubmitReasonForSample(String schoolDetailsIdS, String userIdS, String reasonForVisitNameS, String tsq, String lsq, String comq, String grmq, String c1q, String c2q, String c3q, String nbq, String dbq, String sbq, String gbq, String sampleOther, String sampleOtherq, String sampleContactPerson, String sampleContactNo, String sampleEmailId, ProgressDialog pd) {
        schoolDetailsId = schoolDetailsIdS;
        userId = userIdS;
        reasonForVisitName = reasonForVisitNameS;

        TinyStepsQty=tsq;
        LittleStepsQty = lsq;
        ComputerQty= comq;
        GrammarQty =grmq;
        Cursive1 =c1q;
        Cursive2 = c2q;
        Cursive3=c3q;
        NoteBookQty=nbq;
        DrawingBookQty=dbq;
        ScrapBookQty=sbq;
        GraphBookQty=gbq;
        SampleOther=sampleOther;
        SampleOtherQty=sampleOtherq;
        SampleContactPerson=sampleContactPerson;
        SampleContactNo=sampleContactNo;
        SampleEmailId=sampleEmailId;

        method = "SubmitReasonForVisitSample";
        progressDialog = pd;

        AsyncCallReasonForVisitSampleWS task = new AsyncCallReasonForVisitSampleWS();
        task.execute();
    }

    public class AsyncCallReasonForVisitSampleWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            responseResult = WebService.SubmitReasonForVisitSample(schoolDetailsId,userId,reasonForVisitName,TinyStepsQty,  LittleStepsQty,  ComputerQty, GrammarQty,  Cursive1,  Cursive2,  Cursive3,  NoteBookQty,  DrawingBookQty,  ScrapBookQty,  GraphBookQty,  SampleOther,  SampleOtherQty,  SampleContactPerson,  SampleContactNo,  SampleEmailId,method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (responseResult.equals("Invalid ReasonForVisit")) {
                progressDialog.dismiss();
            }
            else if (responseResult.equals("No Network Found")) {
                progressDialog.dismiss();
            }
            else {

            }
        }
    }


    public void SubmitReasonForPayment(String schoolDetailsIdS, String userIdS,String  reasonForVisitNameS,String date, String paymentModeS, String paymentAmountS, File imgFile, ProgressDialog pd) {
        schoolDetailsId = schoolDetailsIdS;
        userId = userIdS;
        reasonForVisitName = reasonForVisitNameS;
        paymentDate = date;
        paymentMode = paymentModeS;
        paymentAmount = paymentAmountS;
        paymentImgFile = imgFile;
        method = "SubmitReasonForVisitPayment";
        progressDialog = pd;
        AsyncCallReasonForVisitPaymentWS task = new AsyncCallReasonForVisitPaymentWS();
        task.execute();
    }


    public class AsyncCallReasonForVisitPaymentWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            responseResult = WebService.SubmitReasonForVisitPayment(schoolDetailsId,userId,reasonForVisitName,paymentDate,paymentMode,paymentAmount,paymentImgFile,method);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (responseResult.equals("Invalid ReasonForVisit")) {
                progressDialog.dismiss();
            }
            else if (responseResult.equals("No Network Found")) {
                progressDialog.dismiss();
            }
            else {

            }
        }
    }
}
