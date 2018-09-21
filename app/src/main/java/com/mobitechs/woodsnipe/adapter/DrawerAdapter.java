package com.mobitechs.woodsnipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobitechs.woodsnipe.Attendance;
import com.mobitechs.woodsnipe.MainActivity;
import com.mobitechs.woodsnipe.R;
import com.mobitechs.woodsnipe.School_Details;
import com.mobitechs.woodsnipe.School_List;
import com.mobitechs.woodsnipe.model.DrawerItems;
import com.mobitechs.woodsnipe.sessionManager.SessionManager;
import com.mobitechs.woodsnipe.singleton.DrawerListInstance;

import java.util.ArrayList;


public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private static ArrayList<DrawerItems> itemsArrayList;
    public View v;
    public ViewHolder viewHolder;
    public DrawerLayout drawer;
    int positionOfItem = 0;
    SessionManager sessionManager;
    private Animation mAnimation;
    private Context mContext;

    private static ArrayList<DrawerItems> itemselectedArrayList;
    public static DrawerListInstance drawerListInstance = new DrawerListInstance();

    public DrawerAdapter(ArrayList<DrawerItems> itemsArrayList, ArrayList<DrawerItems> itemselectedArrayList, DrawerLayout drawer) {
        this.drawer = drawer;
        this.itemsArrayList = itemsArrayList;
        this.itemselectedArrayList = itemselectedArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_items, viewGroup, false);
        } else if (i == 1) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_item_text, viewGroup, false);
        }
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        DrawerItems itemsList = itemsArrayList.get(i);
        DrawerItems itemselectedList = itemselectedArrayList.get(i);
        viewHolder.bindDrawerItemArrayList(i, itemsList, itemselectedList);
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (position <= 7) {
            viewType = 0;
        } else if (position > 7) {
            viewType = 1;
        }
        return viewType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView vtextView;
        public ImageView vimageView;
        public View drawerDivider;
        public TextView vtext;

        private DrawerItems itemsList;
        private DrawerItems itemsSelectedList;

        public ArrayList<DrawerItems> drawerItemsArrayList = new ArrayList<DrawerItems>();

        public ViewHolder(View itemView) {
            super(itemView);
            vtextView = (TextView) itemView.findViewById(R.id.drawerItemText);
            vimageView = (ImageView) itemView.findViewById(R.id.drawerItemIcon);
            drawerDivider = (View) itemView.findViewById(R.id.drawerDivider);
            vtext = (TextView) itemView.findViewById(R.id.itemText);
            itemView.setOnClickListener(this);
        }

        public void bindDrawerItemArrayList(int i, DrawerItems draweritemsList, DrawerItems draweritemselectedList) {

            this.itemsList = draweritemsList;
            this.itemsSelectedList = draweritemselectedList;

            if (drawerListInstance.getDrawerItemListImagePositionInstances() != null) {
                positionOfItem = drawerListInstance.getDrawerItemListImagePositionInstances();
            }

            vimageView.setImageResource(itemsList.getIcon());
            vtextView.setText(itemsList.getTittle());

            if (positionOfItem == 0 && itemsList.getIcon() == R.drawable.ic_home_amber_100_24dp) {
                vtextView.setText(itemsSelectedList.getTittle());
                vimageView.setImageResource(itemsSelectedList.getIcon());
                vtextView.setTextColor(v.getResources().getColor(R.color.logoColor));
                drawerDivider.setBackgroundColor(v.getResources().getColor(R.color.logoColor));
            }
            else if (positionOfItem == 1 && itemsList.getIcon() == R.drawable.ic_calendar_amber_100_24dp) {
                vtextView.setText(itemsSelectedList.getTittle());
                vimageView.setImageResource(itemsSelectedList.getIcon());
                vtextView.setTextColor(v.getResources().getColor(R.color.logoColor));
                drawerDivider.setBackgroundColor(v.getResources().getColor(R.color.logoColor));
            }
            else if (positionOfItem == 2 && itemsList.getIcon() == R.drawable.ic_school_amber_100) {
                vtextView.setText(itemsSelectedList.getTittle());
                vimageView.setImageResource(itemsSelectedList.getIcon());
                vtextView.setTextColor(v.getResources().getColor(R.color.logoColor));
                drawerDivider.setBackgroundColor(v.getResources().getColor(R.color.logoColor));
            }
            else if (positionOfItem == 3 && itemsList.getIcon() == R.drawable.ic_power_setting_amber_100) {
                vtextView.setText(itemsSelectedList.getTittle());
                vimageView.setImageResource(itemsSelectedList.getIcon());
                vtextView.setTextColor(v.getResources().getColor(R.color.logoColor));
                drawerDivider.setBackgroundColor(v.getResources().getColor(R.color.logoColor));
            }
        }

        @Override
        public void onClick(View view) {
            positionOfItem = this.getAdapterPosition();
            if (this.getAdapterPosition() == 0) {
                drawer.closeDrawers();
                Intent gotoformhome = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(gotoformhome);
            }
            else if (this.getAdapterPosition() == 1) {
                drawer.closeDrawers();
                Intent gotoformhome = new Intent(view.getContext(), Attendance.class);
                gotoformhome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                view.getContext().startActivity(gotoformhome);
            }
            else if (this.getAdapterPosition() == 2) {
                drawer.closeDrawers();
                Intent gotoformhome = new Intent(view.getContext(), School_List.class);
                gotoformhome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                view.getContext().startActivity(gotoformhome);
            }
            else if (this.getAdapterPosition() == 3) {
                drawer.closeDrawers();
                sessionManager = new SessionManager(v.getContext());
                sessionManager.logoutUser();
            }

            drawerListInstance.setDrawerItemListImagePositionInstances(positionOfItem);
            notifyDataSetChanged();
        }
    }
}
