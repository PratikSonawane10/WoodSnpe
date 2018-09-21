package com.mobitechs.woodsnipe.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobitechs.woodsnipe.School_Details;
import com.mobitechs.woodsnipe.School_List;
import com.mobitechs.woodsnipe.R;
import com.mobitechs.woodsnipe.model.School_Items;

import java.util.ArrayList;
import java.util.List;

public class School_List_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<School_Items> filteredListItems;
    List<School_Items> listItems;
    View v;
    RecyclerView.ViewHolder viewHolder;
    RecyclerView recyclerView;

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
                checkOutDt = filteredListItems.getCheckinDate();
                if(checkOutDt != null){

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Confirmation");
                    builder.setMessage("Do You Really Want To Check In?");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent gotoBill = new Intent(v.getContext(), School_Details.class);
                            gotoBill.putExtra("schoolId",filteredListItems.getSchoolId());
                            v.getContext().startActivity(gotoBill);

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }

            }
            else if(v.getId() == R.id.contactLayout){
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + filteredListItems.getSchoolContactNo()));
                v.getContext().startActivity(callIntent);
            }

        }
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
                        if (row.getSchoolName().toLowerCase().contains(charString.toLowerCase())) {
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