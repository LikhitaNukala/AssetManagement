package com.example.android.myscannerapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Likhita on 30-06-2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumofTabs;
    public PagerAdapter(FragmentManager fm,int nooftabs) {
        super(fm);
        this.mNumofTabs=nooftabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SIMActivity tab1 = new SIMActivity();
                return tab1;
            case 1:
                Apps tab2 = new Apps();
                return tab2;
            case 2:
                Location tab3 = new Location();
                return tab3;
            case 4:
                //Wifi tab4=new Wifi();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumofTabs;
    }
}
