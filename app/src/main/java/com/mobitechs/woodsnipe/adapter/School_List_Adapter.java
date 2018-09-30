package com.mobitechs.woodsnipe.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.mobitechs.woodsnipe.MainActivity;
import com.mobitechs.woodsnipe.School_Details;
import com.mobitechs.woodsnipe.School_List;
import com.mobitechs.woodsnipe.R;
import com.mobitechs.woodsnipe.model.School_Items;
import com.mobitechs.woodsnipe.sessionManager.SessionManager;
import com.mobitechs.woodsnipe.webService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class School_List_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<School_Items> filteredListItems;
    List<School_Items> listItems;
    View v;
    RecyclerView.ViewHolder viewHolder;
    RecyclerView recyclerView;

    String userId,schoolId, method, punchInMsg,responseResultPunchIn,title,dialogFor,msg;
    Drawable icon;

    public School_List_Adapter(School_List school_list, List<School_Items> filteredListItems, RecyclerView recyclerView) {
        this.filteredListItems = filteredListItems;
        this.listItems = filteredListItems;
        this.recyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.school_list_items, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder vHolder = (ViewHolder) viewHolder;
            School_Items itemOflist = filteredListItems.get(position);
            vHolder.bindListDetails(itemOflist );
        }
    }

    @Override
    public int getItemCount() {
        return filteredListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtSchoolName,txtAddress,txtEmail,txtContact;
        public View cardView;
        String checkInDt,checkOutDt;

        School_Items filteredListItems = new School_Items();

        public ViewHolder(View itemView) {
            super(itemView);

            LinearLayout contactLayout;
            contactLayout = (LinearLayout) itemView.findViewById(R.id.contactLayout);
            txtSchoolName = (TextView) itemView.findViewById(R.id.txtSchoolName);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            txtContact = (TextView) itemView.findViewById(R.id.txtContact);

            SessionManager sessionManager = new SessionManager(v.getContext());
            HashMap<String, String> typeOfUser = sessionManager.getUserDetails();
            userId = typeOfUser.get(SessionManager.KEY_USERID);

            cardView = itemView;
            cardView.setOnClickListener(this);
        }

        public void bindListDetails(final School_Items filteredListItems) {
            this.filteredListItems = filteredListItems;

            checkInDt = filteredListItems.getCheckinDate();
            checkOutDt = filteredListItems.getCheckinDate();

            if(!checkInDt.equals("") && !checkOutDt.equals("")){
                cardView.setBackgroundColor(v.getResources().getColor(R.color.lightgray02));
            }

            txtSchoolName.setText(filteredListItems.getSchoolName());
            txtAddress.setText(filteredListItems.getSchoolAddress());
            txtEmail.setText(filteredListItems.getSchoolEmail());
            txtContact.setText(filteredListItems.getSchoolContactNo());
        }

        @Override
        public void onClick(final View v) {

            if(this.filteredListItems != null){

                Intent gotoBill = new Intent(v.getContext(), School_Details.class);
                gotoBill.putExtra("schoolId",filteredListItems.getSchoolId());
                gotoBill.putExtra("schoolName",filteredListItems.getSchoolName());
                v.getContext().startActivity(gotoBill);
//                checkOutDt = filteredListItems.getCheckinDate();
//                schoolId = filteredListItems.getSchoolId();
//
//                if(checkOutDt != null){
//                    title="Confirmation";
//                    dialogFor ="CheckIn";
//                    msg = "Do You Want To Check In ?";
//                    ShowConfirmationDialog(dialogFor,msg,title);
//                }else {
//                    // if same date and ou punch is done then give popup u already punch out
//                }
            }
            else if(v.getId() == R.id.contactLayout){
//                Intent callIntent = new Intent(Intent.ACTION_DIAL);
//                callIntent.setData(Uri.parse("tel:" + filteredListItems.getSchoolContactNo()));
//                v.getContext().startActivity(callIntent);
            }

        }
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
                //ShowConfirmationDialog(dialogFor,msg,title);
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
                        punchInMsg = obj.getString("msg");//Already Punch In //Not Punch In

                        if (punchInMsg.equals("Punch In")) {
                            Intent gotoBill = new Intent(v.getContext(), School_Details.class);
                            gotoBill.putExtra("schoolId",schoolId);
                            v.getContext().startActivity(gotoBill);
                        }
                        else {
                            title="Result";
                            dialogFor ="NotPunching";
                            msg = "You Have Not Punch In Yet,Please Punch In First.";
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
            icon = v.getContext().getResources().getDrawable(R.drawable.ic_info_outline_orange_24dp);
        }
        new MaterialStyledDialog.Builder(v.getContext())
                // .setTitle(title)
                .setHeaderColor(R.color.colorPrimaryDark)
                .setDescription(msg)
                .setPositiveText("Ok")
                .setIcon(icon)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        if(dialogFor.equals("CheckIn")){
                            CheckInOrNotWebService taskPunch = new CheckInOrNotWebService();
                            taskPunch.execute();
                        }
                        else if(dialogFor.equals("NotPunching")){
                            Intent it = new Intent(v.getContext(), MainActivity.class);
                            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            v.getContext().startActivity(it);
                        }
                    }
                })
                .show();

    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredListItems = listItems;
                }
                else {
                    List<School_Items> filteredList = new ArrayList<>();
                    for (School_Items row : listItems) {
                        if (row.getSchoolName().toLowerCase().contains(charString.toLowerCase()) || row.getSchoolAddress().toLowerCase().contains(charString.toLowerCase()) || row.getSchoolContactNo().toLowerCase().contains(charString.toLowerCase()) || row.getSchoolEmail().toLowerCase().contains(charString.toLowerCase())  ) {
                            filteredList.add(row);
                        }
                    }
                    filteredListItems = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredListItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredListItems = (ArrayList<School_Items>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}