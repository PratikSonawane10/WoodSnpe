package com.mobitechs.woodsnipe;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobitechs.woodsnipe.adapter.DrawerAdapter;
import com.mobitechs.woodsnipe.model.DrawerItems;
import com.mobitechs.woodsnipe.sessionManager.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;


public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;
    SessionManager sessionManager;
    TextView lblName;
    //TextView lbluserId;
    RecyclerView listItems;
    DrawerLayout drawer;
    FrameLayout frameLayout;
    LinearLayout linearLayout;
    DrawerAdapter drawerAdapter;
    String userType;

    public ArrayList<DrawerItems> itemArrayList;
    public ArrayList<DrawerItems> itemSelectedArrayListForNgo;

    public ArrayList<DrawerItems> itemArrayListForNgo;
    public ArrayList<DrawerItems> itemSelectedArrayList;

    @Override
    public void setContentView(int layoutResID) {
        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.drawer, null);
        frameLayout = (FrameLayout) drawer.findViewById(R.id.contentFrame);
        linearLayout = (LinearLayout) drawer.findViewById(R.id.drawerlinearlayout);
        listItems = (RecyclerView) drawer.findViewById(R.id.drawerListItem);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listItems.setLayoutManager(linearLayoutManager);

        SessionManager sessionManagerNgo = new SessionManager(BaseActivity.this);
        HashMap<String, String> typeOfUser = sessionManagerNgo.getUserDetails();
        userType = typeOfUser.get(SessionManager.KEY_USERID);

        final String[] tittle = new String[]{"Home", "Attendance","School List", "Logout"};
        final int[] icons = new int[]{R.drawable.ic_home_amber_100_24dp, R.drawable.ic_calendar_amber_100_24dp, R.drawable.ic_school_amber_100, R.drawable.ic_power_setting_amber_100};
        itemArrayList = new ArrayList<DrawerItems>();
        for (int i = 0; i < tittle.length; i++) {
            DrawerItems drawerItems = new DrawerItems();
            drawerItems.setTittle(tittle[i]);
            drawerItems.setIcons(icons[i]);
            itemArrayList.add(drawerItems);
        }
        final int[] selectedicons = new int[]{R.drawable.ic_home_yellow, R.drawable.ic_calendar_yellow_24dp, R.drawable.ic_school_yellow, R.drawable.ic_power_yellow};
        itemSelectedArrayList = new ArrayList<DrawerItems>();
        for (int i = 0; i < tittle.length; i++) {
            DrawerItems drawerItems = new DrawerItems();
            drawerItems.setTittle(tittle[i]);
            drawerItems.setIcons(selectedicons[i]);
            itemSelectedArrayList.add(drawerItems);
        }
        drawerAdapter = new DrawerAdapter(itemArrayList, itemSelectedArrayList, drawer);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        getLayoutInflater().inflate(layoutResID, linearLayout, true);
        drawer.setClickable(true);
        drawerAdapter.notifyDataSetChanged();
        listItems.setAdapter(drawerAdapter);


        toolbar = (Toolbar) drawer.findViewById(R.id.app_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        super.setContentView(drawer);
        sessionManager = new SessionManager(getApplicationContext());
        lblName = (TextView) findViewById(R.id.lblName);
        //lbluserId = (TextView) findViewById(R.id.lblUserId);

        HashMap<String, String> user = sessionManager.getUserDetails();

        //String userId = user.get(sessionManager.KEY_USERID);
        String name = user.get(SessionManager.KEY_NAME);  // get Name
//        name= "Pratik Sonawane";
        lblName.setText(name);   // Show user data on activity
        //lbluserId.setText(userId);

    }
}