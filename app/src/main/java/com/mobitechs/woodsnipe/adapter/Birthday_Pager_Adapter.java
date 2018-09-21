package com.mobitechs.woodsnipe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mobitechs.woodsnipe.BDay_Today_Tab;
import com.mobitechs.woodsnipe.BDay_Tomorrow_Tab;
import com.mobitechs.woodsnipe.BDay_Yesterday_Tab;

public class Birthday_Pager_Adapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public Birthday_Pager_Adapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BDay_Yesterday_Tab tab1 = new BDay_Yesterday_Tab();
                return tab1;
            case 1:
                BDay_Today_Tab tab2 = new BDay_Today_Tab();
                return tab2;
            case 2:
                BDay_Tomorrow_Tab tab3 = new BDay_Tomorrow_Tab();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
